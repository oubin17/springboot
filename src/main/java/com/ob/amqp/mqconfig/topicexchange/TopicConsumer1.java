package com.ob.amqp.mqconfig.topicexchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2019/8/13 11:35
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "q_topic_message")
public class TopicConsumer1 {

    @RabbitHandler
    public void process(String msg) {
        log.info("topic_consumer1 : {}", msg);
    }
}
