package com.ob.work.trade.service;

import com.ob.common.base.service.CustomService;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import com.ob.test.redis.service.RedisLock;
import com.ob.work.trade.constant.Constants;
import com.ob.work.trade.domain.Goods;
import com.ob.work.trade.dto.GoodsUpdateDto;
import com.ob.work.trade.enums.GoodsStateEnum;
import com.ob.work.trade.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/9/23 11:35
 * @Description:
 */
@Slf4j
@Service
public class GoodsService extends CustomService<Goods, String> {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ValueOperations valueOperations;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisLock redisLock;

    private static long KEY_EXPIRE_TIME = 5 * 60L;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    public Goods getGoodsById(String id) {
        Goods goods = (Goods) redisTemplate.opsForValue().get(Constants.GOODS_INFO_KEY + id);

        if (null == goods) {
            String key = Constants.GOODS_ID_LOCK + id;
            String value = UUID.randomUUID().toString();
            try {
                boolean lock = redisLock.lock(key, value);
                if (lock) {
                    goods = (Goods) redisTemplate.opsForValue().get(Constants.GOODS_INFO_KEY + id);
                    if (null != goods) {
                        return goods;
                    }
                    goods = goodsRepository.findOne(id);
                    if (null != goods) {
                        redisTemplate.opsForValue().set(Constants.GOODS_INFO_KEY + id, goods);
                        return goods;
                    } else {
                        Goods goodsDefault = new Goods();
                        goodsDefault.setId("-1");
                        redisTemplate.opsForValue().set(Constants.GOODS_INFO_KEY + id, goodsDefault);
                        redisTemplate.expire(Constants.GOODS_INFO_KEY + id, KEY_EXPIRE_TIME, TimeUnit.SECONDS);
                        return goodsDefault;
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(ErrorCode.BAD_REQUEST, "商品详情获取失败");
            } finally {
                redisLock.unlock(key, value);
            }
        } else {
            return goods;
        }

    }

    /**
     * 修改商品名称
     *
     * @param id
     * @param name
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Goods updateGoodsByName(String id, String name) {
        int update = goodsRepository.updateByGoodsName(id, name);
        if (update > 0) {
            redisTemplate.delete(Constants.GOODS_INFO_KEY + id);
        }
        return getGoodsById(id);
    }


    /**
     * 简化流程,创建商品即开售,抢购期为1天
     *
     * @param saveDto
     */
    public Goods addGoodsInfo(GoodsUpdateDto saveDto) {
        Goods goods = new Goods();
        goods.setGoodsName(saveDto.getGoodsName());
        goods.setRemainingQuantity(saveDto.getRemainingQuantity());
        goods.setOnSaleTime(System.currentTimeMillis());
        goods.setExpireTime(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        goods.setState(GoodsStateEnum.ONSALE.getState());
        goodsRepository.save(goods);
        cacheWarmUp(goods);
        return goods;
    }

    /**
     * 简单缓存预热
     *
     * @param goods
     */
    @SuppressWarnings("unchecked")
    private void cacheWarmUp(Goods goods) {
        long expireTime = goods.getExpireTime() - System.currentTimeMillis();
        valueOperations.set(Constants.GOODS_ID_KEY + goods.getId(), goods.getRemainingQuantity(), expireTime, TimeUnit.MILLISECONDS);
    }


}
