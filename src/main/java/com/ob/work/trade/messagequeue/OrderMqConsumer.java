package com.ob.work.trade.messagequeue;

import com.ob.common.util.JsonUtil;
import com.ob.work.trade.domain.Order;
import com.ob.work.trade.enums.OrderStateEnum;
import com.ob.work.trade.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oubin
 * @Date: 2019/9/24 17:01
 * @Description:
 */
@Slf4j
@Component
public class OrderMqConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @RabbitListener(queues = OrderMqConfig.ORDER_DEAD_LETTER_QUEUE)
    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void process(Message msg) {
        log.info("消费队列收到消息 : {}", msg);
        Order order = JsonUtil.jsonToBean(msg.toString(), Order.class);
        orderRepository.expireOrder(order.getId(), OrderStateEnum.EXPIRED.getState(), System.currentTimeMillis());
    }
}
