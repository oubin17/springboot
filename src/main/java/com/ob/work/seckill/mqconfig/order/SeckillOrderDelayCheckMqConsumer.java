package com.ob.work.seckill.mqconfig.order;

import com.ob.common.util.JsonUtil;
import com.ob.work.seckill.dto.mq.SeckillOrderMQDTO;
import com.ob.work.seckill.entities.SeckillOrder;
import com.ob.work.seckill.repository.SeckillOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/12/5 10:40
 * @Description:
 */
@Slf4j
@Component
public class SeckillOrderDelayCheckMqConsumer {

    @Autowired
    private SeckillOrderRepository orderRepository;

    /**
     * 接收到延时队列的消息，检查订单是否成功创建，如果不成功，通知上游
     *
     * @param msg
     */
    @RabbitListener(queues = SeckillOrderDelayCheckMq.SECKILL_ORDER_DEAD_LETTER_DIRECT_QUEUE)
    @RabbitHandler
    public void process(Message msg) {
        try {
            SeckillOrderMQDTO seckillOrderMQDTO = JsonUtil.byteArrayToBean(msg.getBody(), SeckillOrderMQDTO.class);
            SeckillOrder order = orderRepository.findOne(seckillOrderMQDTO.getOrderId());
            if (null != order) {
                log.info("创建订单成功");
            } else {
                log.info("创建订单失败,库存数量+1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("接收延时队列消息失败:{}", msg.toString());
        }


    }
}
