package com.ob.work.seckill.mqconfig.order;

import com.ob.common.config.rabbitmq.RabbitProducer;
import com.ob.common.constant.TimeConstants;
import com.ob.common.util.JsonUtil;
import com.ob.work.seckill.dto.mq.SeckillOrderMQDTO;
import com.ob.work.seckill.entities.SeckillOrder;
import com.ob.work.seckill.enums.OrderStateEnum;
import com.ob.work.seckill.repository.SeckillOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/12/4 16:22
 * @Description:
 */
@Slf4j
@Component
public class SeckillOrderCreateMqConsumer {

    @Autowired
    private SeckillOrderRepository orderRepository;

    @Autowired
    private RabbitProducer rabbitProducer;

    /**
     * 这里更改预创建订单状态为待支付（设置创建订单超时时间为一分钟，超过期限，订单创建失败，回滚库存）
     *
     * @param msg
     */
    @RabbitListener(queues = SeckillOrderCreateMq.SECKILL_ORDER_CREATE_QUEUE)
    @RabbitHandler
    public void process(Message msg) {
        try {
            SeckillOrderMQDTO seckillOrderMQDTO = JsonUtil.byteArrayToBean(msg.getBody(), SeckillOrderMQDTO.class);
            if (null != seckillOrderMQDTO) {
                long currentTimeMillis = System.currentTimeMillis();
                SeckillOrder order = orderRepository.strictFind(seckillOrderMQDTO.getOrderId());
                if (currentTimeMillis - seckillOrderMQDTO.getOrderPreCreateTime() < TimeConstants.MILLSECONDS_PRE_MINUTE) {
                    order.setState(OrderStateEnum.UNPAID.getState());
                    seckillOrderMQDTO.setOrderState(OrderStateEnum.UNPAID.getState());
                } else {
                    //TODO 订单超时未创建,库存回滚
                    order.setState(OrderStateEnum.CREATE_TIMEOUT.getState());
                    seckillOrderMQDTO.setOrderState(OrderStateEnum.CREATE_TIMEOUT.getState());
                }
                orderRepository.save(order);
                seckillOrderMQDTO.setOrderCreateTime(currentTimeMillis);
                //将处理后的MQ消息再次入队列,需要通知其他端订单创建成功
                rabbitProducer.sendMsg(SeckillOrderConfirmMq.SECKILL_ORDER_CONFIRM_TOPIC_EXCHANGE,
                        SeckillOrderConfirmMq.SECKILL_ORDER_CONFIRM_BINDING_KEY,
                        JsonUtil.toJson(seckillOrderMQDTO));
                //并且成功创建订单之后，需要保证订单在15分钟内支付成功，实现方案可以将订单发送到15分钟过期的延时队列，然后消费端检查订单状态
                //该方案在trade目录下已经实现，这里不重复实现。
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("接收消息失败:{}", msg.toString());
        }



    }
}
