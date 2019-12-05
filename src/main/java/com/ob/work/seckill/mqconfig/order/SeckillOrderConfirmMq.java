package com.ob.work.seckill.mqconfig.order;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/12/4 20:00
 * @Description: 消费者创建订单之后，将创建订单成功的消息发送到mq，由下游消费者消费
 */
@Configuration
public class SeckillOrderConfirmMq {

    /**
     * 创建订单队列交换机
     */
    public final static String SECKILL_ORDER_CONFIRM_TOPIC_EXCHANGE = "seckill_order_confirm_topic_exchange";

    /**
     * 创建订单队列名称
     */
    public final static String SECKILL_ORDER_CONFIRM_QUEUE = "seckill_order_confirm_queue";

    /**
     * 路由键
     */
    private final static String SECKILL_ORDER_CONFIRM_ROUTING_KEY = "seckill_order.confirm.#";

    /**
     * 绑定键
     */
    public final static String SECKILL_ORDER_CONFIRM_BINDING_KEY = "seckill_order.confirm.check";

    /**
     * 声明创建订单队列
     *
     * @return
     */
    @Bean
    public Queue seckillOrderConfirmQueue() {
        return new Queue(SECKILL_ORDER_CONFIRM_QUEUE);
    }

    /**
     * 声明创建订单direct交换器
     *
     * @return
     */
    @Bean
    public TopicExchange seckillOrderConfirmExchange() {
        return new TopicExchange(SECKILL_ORDER_CONFIRM_TOPIC_EXCHANGE);
    }

    /**
     * 绑定创建订单队列到交换机
     *
     * @param seckillOrderConfirmQueue
     * @param seckillOrderConfirmExchange
     * @return
     */
    @Bean
    public Binding seckillOrderConfirmBinding(Queue seckillOrderConfirmQueue, TopicExchange seckillOrderConfirmExchange) {
        return BindingBuilder.bind(seckillOrderConfirmQueue).to(seckillOrderConfirmExchange).with(SECKILL_ORDER_CONFIRM_ROUTING_KEY);
    }
}
