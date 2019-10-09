package com.ob.work.trade.messagequeue;

import com.ob.work.trade.constant.Constants;
import com.ob.work.trade.entity.Goods;
import com.ob.work.trade.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/9/25 16:49
 * @Description:
 */
@Slf4j
@Component
public class GoodsMqConsumer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @RabbitListener(queues = GoodsMqConfig.GOODS_ORDER_REBUILD_QUEUE,
            containerFactory = "customerContainerFactory")
    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void process(Message msg) {
        String id = new String(msg.getBody());
        try {
            Goods goods = goodsRepository.strictFind(id);
            redisTemplate.opsForValue().set(Constants.GOODS_INFO_KEY + id, goods);
        } catch (Exception e) {
            log.info("获取商品详情失败,商品ID:{}", id);
        }
    }
}
