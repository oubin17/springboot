package com.ob.work.seckill.mqconfig.order;

import com.ob.common.constant.TimeConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/12/5 10:37
 * @Description: 死信队列负责接收延迟消息，检查订单是否创建成功
 */
@Configuration
public class SeckillOrderDelayCheckMq {

    /**
     * 延迟队列交换机
     */
    public final static String SECKILL_ORDER_DELAY_CHECK_EXCHANGE = "seckill_order_delay_check_exchange";

    /**
     * 延迟队列
     */
    public final static String SECKILL_ORDER_DELAY_CHECK_QUEUE = "seckill_order_delay_check_queue";

    /**
     * 延迟队列路由键
     */
    public final static String SECKILL_ORDER_DELAY_CHECK_ROUTING_KEY = "seckill_order_delay_check_routing_key";

    /**
     * 死信交换机
     */
    public final static String SECKILL_ORDER_DEAD_LETTER_EXCHANGE = "seckill_order_dead_letter_exchange";

    /**
     * 死信队列
     */
    public final static String SECKILL_ORDER_DEAD_LETTER_DIRECT_QUEUE = "seckill_order_dead_letter_direct_queue";

    /**
     * 死信队列路由键
     */
    public final static String SECKILL_ORDER_DEAD_LETTER_ROUTING_KEY = "seckill_order_dead_letter_routing_key";


    /**
     * 创建订单延迟检查:2分钟
     */
    private static final Integer DELAY_CHECK_TIME = 2 * TimeConstants.MILLSECONDS_PRE_MINUTE;

    /**
     * DLX, dead letter发送到的exchange
     * dead letter携带的routing key
     * 设置队列的过期时间
     * @return
     */
    @Bean
    public Queue seckillOrderDelayCheckQueueTTL() {
        return QueueBuilder.durable(SECKILL_ORDER_DELAY_CHECK_QUEUE)
                .withArgument("x-dead-letter-exchange", SECKILL_ORDER_DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", SECKILL_ORDER_DEAD_LETTER_ROUTING_KEY)
                .withArgument("x-message-ttl", DELAY_CHECK_TIME)
                .build();
    }

    /**
     * 延迟队列交换器
     *
     * @return
     */
    @Bean
    public DirectExchange seckillOrderDelayCheckExchange() {
        return new DirectExchange(SECKILL_ORDER_DELAY_CHECK_EXCHANGE);
    }

    /**
     * 定义延时队列
     *
     * @param seckillOrderDelayCheckQueueTTL
     * @param seckillOrderDelayCheckExchange
     * @return
     */
    @Bean
    public Binding seckillOrderDelayCheckBinding(Queue seckillOrderDelayCheckQueueTTL, DirectExchange seckillOrderDelayCheckExchange) {
        return BindingBuilder.bind(seckillOrderDelayCheckQueueTTL)
                .to(seckillOrderDelayCheckExchange)
                .with(SECKILL_ORDER_DELAY_CHECK_ROUTING_KEY);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue seckillOrderDeadLetterQueue() {
        return QueueBuilder.durable(SECKILL_ORDER_DEAD_LETTER_DIRECT_QUEUE)
                .build();
    }

    /**
     * 死信队列交换机
     *
     * @return
     */
    @Bean
    public DirectExchange seckillOrderDeadLetterExchange() {
        return new DirectExchange(SECKILL_ORDER_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 绑定消费队列到死信交换机
     *
     * @param seckillOrderDeadLetterQueue
     * @param seckillOrderDeadLetterExchange
     * @return
     */
    @Bean
    public Binding seckillOrderDeadLetterBinding(Queue seckillOrderDeadLetterQueue, DirectExchange seckillOrderDeadLetterExchange) {
        return BindingBuilder.bind(seckillOrderDeadLetterQueue)
                .to(seckillOrderDeadLetterExchange)
                .with(SECKILL_ORDER_DEAD_LETTER_ROUTING_KEY);
    }

}
