package com.ob.test.amqp.mqconfig.topicexchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/8/13 11:37
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "q_topic_messages")
public class TopicConsumer2 {

    @RabbitHandler
    public void process(String msg) {
        log.info("topic_consumer2 : {}", msg);
    }
}
