package com.ob.amqp.mqconfig.fanoutexchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/8/13 13:55
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "q_fanout_A")
public class FanoutConsumer1 {

    @RabbitHandler
    public void process(String msg) {
        log.info("fanout_consumerA : {}", msg);
    }
}
