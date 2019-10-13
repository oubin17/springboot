package com.ob.modelexample.amqp.mqconfig.topicexchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/8/13 10:55
 * @Description:
 */
@Configuration
public class TopicRabbitConfig {

    private final static String MESSAGE = "q_topic_message";

    private final static String MESSAGES = "q_topic_messages";

    @Bean
    public Queue queueMessage() {
        return new Queue(TopicRabbitConfig.MESSAGE);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(TopicRabbitConfig.MESSAGES);
    }

    /**
     * 声明一个Topic类型的交换机
     *
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topic_exchange");
    }

    /**
     * 绑定Queue到交换机，并且制定routingkey
     * 
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }


}
