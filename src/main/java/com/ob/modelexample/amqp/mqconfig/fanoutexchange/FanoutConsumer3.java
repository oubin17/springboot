package com.ob.modelexample.amqp.mqconfig.fanoutexchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/8/13 14:15
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "q_fanout_C")
public class FanoutConsumer3 {

    @RabbitHandler
    public void process(String msg) {
        log.info("fanout_consumerC : {}", msg);
    }
}
