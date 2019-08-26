package com.ob.amqp.service;

import com.ob.amqp.mqconfig.delayqueue.DeadLetterExchangeConfig;
import com.ob.amqp.mqconfig.delayqueue.ExpirationMessagePostProcessor;
import com.ob.amqp.mqconfig.directexchange.DirectRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/8/13 11:41
 * @Description:
 */
@Slf4j
@Service
public class RabbitService {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    /**
     * 简单队列
     * @param message
     */
    public void simpleQueue(String message) {
        rabbitmqTemplate.convertAndSend("o_queue", message);
    }

    /**
     * 主题交换器
     * @param message
     */
    public void topicQueue1(String message) {
        rabbitmqTemplate.convertAndSend("topic_exchange", "topic.message", message);
    }

    public void topicQueue2(String message) {
        rabbitmqTemplate.convertAndSend("topic_exchange", "topic.messages", message);
    }

    /**
     * 扇形交换器
     *
     * @param message
     */
    public void fanoutQueue(String message) {
        rabbitmqTemplate.convertAndSend("fanout_exchange","", message);

    }

    /**
     * 直接交换器
     *
     * @param message
     */
    public void directQueue1(String message) {
        rabbitmqTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_EXCHANGE_ROUTING_KEY_ONE, message);
    }

    public void directQueue2(String message) {
        rabbitmqTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_EXCHANGE_ROUTING_KEY_TWO, message);
    }

    public void delayQueue(String message) {

        for (int i = 1; i < 10; i++) {
            long expiration = i * 1000;
            rabbitmqTemplate.convertAndSend(DeadLetterExchangeConfig.DELAY_EXCHANGE_NAME, DeadLetterExchangeConfig.DELAY_EXCHANGE_ROUTING_KEY,
                     "Message From delay_queue_per_message_ttl with expiration " + expiration, new ExpirationMessagePostProcessor(expiration));
            log.info("已发送第{}条消息到延迟队列", i);
        }

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
