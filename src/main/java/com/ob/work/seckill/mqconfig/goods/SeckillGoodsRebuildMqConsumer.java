package com.ob.work.seckill.mqconfig.goods;

import com.ob.work.seckill.entities.SeckillGoods;
import com.ob.work.seckill.redisdao.SeckillGoodsRedisDao;
import com.ob.work.seckill.repository.SeckillGoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/12/3 14:53
 * @Description:
 */
@Slf4j
@Component
public class SeckillGoodsRebuildMqConsumer {

    @Autowired
    private SeckillGoodsRedisDao seckillGoodsRedisDao;

    @Autowired
    private SeckillGoodsRepository seckillGoodsRepository;

    /**
     * 获取删除缓存失败的键，异步删除缓存(避免缓存中存在的一直是脏数据)
     *
     * @param msg
     */
    @RabbitListener(queues = SeckillGoodsRebuildMq.SECKILL_GOODS_REBUILD_QUEUE)
    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void process(Message msg) {
        String id = new String(msg.getBody());
        try {
            SeckillGoods seckillGoods = seckillGoodsRepository.strictFind(id);
            long expireTime = seckillGoods.getExpireTime() - System.currentTimeMillis();
            seckillGoodsRedisDao.saveValueExpireTime(seckillGoods.getId(), seckillGoods, expireTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("删除缓存失败，缓存id:{}", id);
        }

    }
}
