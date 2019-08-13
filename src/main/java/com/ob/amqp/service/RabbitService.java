package com.ob.amqp.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/8/13 11:41
 * @Description:
 */
@Service
public class RabbitService {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void simpleQueue(String message) {
        rabbitmqTemplate.convertAndSend("o_queue", message);
    }

    public void topicQueue1(String message) {
        rabbitmqTemplate.convertAndSend("topic_exchange", "topic.message", message);
    }

    public void topicQueue2(String message) {
        rabbitmqTemplate.convertAndSend("topic_exchange", "topic.messages", message);
    }

    public void fanoutQueue(String message) {
        rabbitmqTemplate.convertAndSend("fanout_exchange","", message);

    }

}
