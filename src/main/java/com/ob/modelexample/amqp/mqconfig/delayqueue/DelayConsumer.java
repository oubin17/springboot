package com.ob.modelexample.amqp.mqconfig.delayqueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/8/14 10:08
 * @Description:
 */
@Slf4j
@Component
public class DelayConsumer {

    @RabbitListener(queues = DeadLetterExchangeConfig.DEAD_LETTER_QUEUE_NAME)
    @RabbitHandler
    public void process(String msg) {
        log.info("消费队列收到消息 : {}", msg);
    }
}
