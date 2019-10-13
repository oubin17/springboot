package com.ob.modelexample.amqp.mqconfig.delayqueue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/8/14 09:08
 * @Description:
 */
@Configuration
public class DeadLetterExchangeConfig {
    /**
     * 延迟队列
     */
    public static final String DELAY_EXCHANGE_NAME = "delay_exchange";

    private static final String DELAY_QUEUE_WITHOUT_QUEUE_EXPIRATION = "delay_queue_without_queue_expiration";

    private static final String DELAY_QUEUE_WITH_QUEUE_EXPIRATION = "delay_queue_with_queue_expiration";

    public static final String DELAY_EXCHANGE_ROUTING_KEY = "delay_exchange_routing_key";

    /**
     * 死信队列
     */
    static final String DEAD_LETTER_QUEUE_NAME = "dead_letter_queue";

    private static final String DEAD_LETTER_EXCHANGE_NAME = "dead_letter_exchange";

    private static final String DEAD_LETTER_QUEUE_ROUTING_KEY = "dead_letter_process_routing_key";

    private static final Long QUEUE_EXPIRATION = 10000L;

    @Bean
    public Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_WITHOUT_QUEUE_EXPIRATION)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ROUTING_KEY)
                .build();
    }

    /**
     * DLX, dead letter发送到的exchange
     * dead letter携带的routing key
     * 设置队列的过期时间
     *
     * @return
     */
    @Bean
    public Queue delayQueuePerQueueTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_WITH_QUEUE_EXPIRATION)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ROUTING_KEY)
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadLetterMessageBinding(Queue delayQueuePerMessageTTL, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueuePerMessageTTL)
                .to(delayExchange)
                .with(DELAY_EXCHANGE_ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterQueueBinding(Queue delayQueuePerQueueTTL, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueuePerQueueTTL)
                .to(delayExchange)
                .with(DELAY_EXCHANGE_ROUTING_KEY);
    }

    /**
     * 实际消费队列
     *
     * @return
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }

    /**
     * dlx : 死信交换机
     * @return
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE_NAME);
    }

    /**
     * 绑定消费队列到死信交换机
     *
     * @param deadLetterQueue
     * @param deadLetterExchange
     * @return
     */
    @Bean
    public Binding dlxBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with(DEAD_LETTER_QUEUE_ROUTING_KEY);
    }

}
