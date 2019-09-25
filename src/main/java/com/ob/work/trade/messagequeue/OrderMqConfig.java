package com.ob.work.trade.messagequeue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/9/24 16:39
 * @Description:
 */
@Configuration
public class OrderMqConfig {

    /**
     * 延迟队列路由键
     */
    public static final String ORDER_DELAY_EXCHANGE_ROUTING_KEY = "order_delay_exchange_routing_key";

    /**
     * 延时队列交换机
     */
    public static final String ORDER_DELAY_EXCHANGE = "order_delay_exchange";

    /**
     * 队列名称
     */
    private static final String ORDER_DELAY_QUEUE_WITH_QUEUE_EXPIRATION = "order_delay_queue_with_queue_expiration";


    /**
     * 死信队列
     */
    static final String ORDER_DEAD_LETTER_QUEUE = "order_dead_letter_queue";

    /**
     * 死信队列交换机
     */
    private static final String ORDER_DEAD_LETTER_EXCHANGE = "order_dead_letter_exchange";

    /**
     * 死信队列路由键
     */
    private static final String ORDER_DEAD_LETTER_QUEUE_ROUTING_KEY = "order_dead_letter_queue_routing_key";

    /**
     * 订单过期时间:15分钟
     */
    private static final Long QUEUE_EXPIRATION = 15 * 60 * 1000L;

    /**
     * DLX, dead letter发送到的exchange
     * dead letter携带的routing key
     * 设置队列的过期时间
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueuePerQueueTTL() {
        return QueueBuilder.durable(ORDER_DELAY_QUEUE_WITH_QUEUE_EXPIRATION)
                .withArgument("x-dead-letter-exchange", ORDER_DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_DEAD_LETTER_QUEUE_ROUTING_KEY)
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 延时队列交换机
     *
     * @return
     */
    @Bean
    public DirectExchange orderDelayExchange() {
        return new DirectExchange(ORDER_DELAY_EXCHANGE);
    }

    /**
     * 绑定延时队列和交换机
     *
     * @param orderDelayQueuePerQueueTTL
     * @param orderDelayExchange
     * @return
     */
    @Bean
    public Binding deadLetterQueueBinding(Queue orderDelayQueuePerQueueTTL, DirectExchange orderDelayExchange) {
        return BindingBuilder.bind(orderDelayQueuePerQueueTTL)
                .to(orderDelayExchange)
                .with(ORDER_DELAY_EXCHANGE_ROUTING_KEY);
    }

    /**
     * 实际消费队列
     *
     * @return
     */
    @Bean
    public Queue orderDeadLetterQueue() {
        return QueueBuilder.durable(ORDER_DEAD_LETTER_QUEUE)
                .build();
    }

    /**
     * dlx : 死信交换机
     * @return
     */
    @Bean
    public DirectExchange orderDeadLetterExchange() {
        return new DirectExchange(ORDER_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 绑定消费队列到死信交换机
     *
     * @param orderDeadLetterQueue
     * @param orderDeadLetterExchange
     * @return
     */
    @Bean
    public Binding dlxBinding(Queue orderDeadLetterQueue, DirectExchange orderDeadLetterExchange) {
        return BindingBuilder.bind(orderDeadLetterQueue)
                .to(orderDeadLetterExchange)
                .with(ORDER_DEAD_LETTER_QUEUE_ROUTING_KEY);
    }

}
