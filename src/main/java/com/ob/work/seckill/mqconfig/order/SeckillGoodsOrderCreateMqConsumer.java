package com.ob.work.seckill.mqconfig.order;

import com.ob.work.seckill.entities.SeckillOrder;
import com.ob.work.seckill.enums.OrderStateEnum;
import com.ob.work.seckill.repository.SeckillOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/12/4 16:22
 * @Description:
 */
@Slf4j
@Component
public class SeckillGoodsOrderCreateMqConsumer {

    @Autowired
    private SeckillOrderRepository orderRepository;

    /**
     * 这里更改预创建订单状态为待支付（可以设置一个超时时间，超过改期限，直接让预创建订单失效）
     *
     * @param msg
     */
    @RabbitListener(queues = SeckillGoodsOrderCreateMq.SECKILL_ORDER_CREATE_QUEUE)
    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void process(Message msg) {
        String id = new String(msg.getBody());
        try {
            SeckillOrder order = orderRepository.strictFind(id);
            if (OrderStateEnum.PRE_CREATE.getState() == order.getState()) {
                order.setState(OrderStateEnum.UNPAID.getState());
                orderRepository.save(order);
            } else {
                //TODO 如果订单不是预下单状态，就下单失败，库存回滚
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("创建订单失败，订单id{}", id);
        }

    }
}
