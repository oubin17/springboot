package com.ob.work.seckill.service;

import com.ob.common.config.rabbitmq.RabbitProducer;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import com.ob.modelexample.redis.service.RedisLock;
import com.ob.work.seckill.dto.SeckillGoodsReqDTO;
import com.ob.work.seckill.dto.SeckillGoodsResDTO;
import com.ob.work.seckill.entities.SeckillGoods;
import com.ob.work.seckill.entities.SeckillGoodsInventory;
import com.ob.work.seckill.mqconfig.goods.SeckillGoodsRebuildMq;
import com.ob.work.seckill.redisdao.SeckillGoodsInventoryRedisDao;
import com.ob.work.seckill.redisdao.SeckillGoodsRedisDao;
import com.ob.work.seckill.repository.SeckillGoodsInventoryRepository;
import com.ob.work.seckill.repository.SeckillGoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/12/2 09:14
 * @Description:
 */
@Slf4j
@Service
public class SeckillGoodsService {

    @Autowired
    private SeckillGoodsRepository goodsRepository;

    @Autowired
    private SeckillGoodsInventoryRepository inventoryRepository;

    @Autowired
    private SeckillGoodsRedisDao goodsRedisDao;

    @Autowired
    private SeckillGoodsInventoryRedisDao inventoryRedisDao;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RabbitProducer rabbitProducer;

    /**
     * 新增热点商品,并添加商品库存，商品库存缓存
     *
     * @param goodsReqDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SeckillGoodsResDTO addSeckillGoods(SeckillGoodsReqDTO goodsReqDTO) {
        SeckillGoods seckillGoods = goodsReqDTO.seckillGoodsConverter();
        seckillGoods.setOnSaleTime(System.currentTimeMillis());
        seckillGoods.setExpireTime(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        goodsRepository.save(seckillGoods);
        seckillGoodsCacheWarmUp(seckillGoods);

        //库存预加载
        SeckillGoodsInventory inventory = new SeckillGoodsInventory();
        inventory.setGoodsId(seckillGoods.getId());
        inventory.setRemainingQuantity(goodsReqDTO.getRemainingQuantity());
        inventoryRepository.save(inventory);
        seckillGoodsInventoryCacheWarmUp(inventory, seckillGoods.getExpireTime() - System.currentTimeMillis());

        return seckillGoods.convertToSeckillGoodsResDTO();
    }

    /**
     * 根据id获取缓存对象:先从缓存中获取，有则直接返回，没有则获取锁，再次从缓存中读（其他获取锁的线程可能将对象写入缓存了），读不到再读数据库，写缓存。
     *
     * @param id
     * @return
     */
    public SeckillGoodsResDTO getGoodsById(String id) {
        SeckillGoods seckillGoods = goodsRedisDao.getValue(id);
        if (null == seckillGoods) {
            String key = goodsRedisDao.seckillGoodsKeyLock(id);
            String value = UUID.randomUUID().toString();
            try {
                boolean lock = redisLock.lock(key, value);
                if (lock) {
                    seckillGoods = goodsRedisDao.getValue(id);
                    if (null != seckillGoods) {
                        return seckillGoods.convertToSeckillGoodsResDTO();
                    }
                    seckillGoods = goodsRepository.findOne(id);
                    if (null == seckillGoods) {
                        SeckillGoods defaultGoods = new SeckillGoods();
                        defaultGoods.setId(id);
                        goodsRedisDao.saveValueDefaultExpireTime(id, defaultGoods);
                        return null;
                    } else {
                        seckillGoodsCacheWarmUp(seckillGoods);
                        return seckillGoods.convertToSeckillGoodsResDTO();
                    }
                } else {
                    //TODO 采取自旋的方式不断获取锁，达到一定次数还未获取到锁直接退出
                    return null;
                }
            } catch (Exception e) {
                throw new BizException(ErrorCode.SECKILL_GOODS_LOCK_ERROR);
            } finally {
                //出现异常直接释放锁资源，避免其他线程无法及时获取到锁
                redisLock.unlock(key, value);
            }
        } else {
            return seckillGoods.convertToSeckillGoodsResDTO();
        }

    }

    /**
     * 更新缓存，这里涉及到经典的cache aside pattern （缓存模式）更新数据库，再删除缓存，如果删除失败，就异步删除
     *
     * @param id
     * @param name
     * @return
     */
    public SeckillGoodsResDTO updateSeckillGoods(String id, String name) {
        SeckillGoods seckillGoods = goodsRepository.strictFind(id);
        seckillGoods.setGoodsName(name);
        goodsRepository.saveAndFlush(seckillGoods);
        try {
            goodsRedisDao.delete(id);
        } catch (Exception e) {
            rabbitProducer.sendMsg(SeckillGoodsRebuildMq.SECKILL_GOODS_REBUILD_TOPIC_EXCHANGE,
                    SeckillGoodsRebuildMq.SECKILL_GOODS_REBUILD_BINDING_KEY,
                    id);
            log.info("重建缓存,key{}", id);
        }
        return seckillGoods.convertToSeckillGoodsResDTO();
    }

    /**
     * 商品缓存预热
     *
     * @param goods
     */
    @SuppressWarnings("unchecked")
    private void seckillGoodsCacheWarmUp(SeckillGoods goods) {
        long expireTime = goods.getExpireTime() - System.currentTimeMillis();
        goodsRedisDao.saveValueExpireTime(goods.getId(), goods, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 商品库存缓存预热
     *
     * @param inventory
     * @param expireTime
     */
    private void seckillGoodsInventoryCacheWarmUp(SeckillGoodsInventory inventory, Long expireTime) {
        inventoryRedisDao.saveValueExpireTime(inventory.getGoodsId(), inventory.getRemainingQuantity(), expireTime, TimeUnit.MILLISECONDS);
    }




}
