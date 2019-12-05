package com.ob.work.seckill.mqconfig.order;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/12/4 15:36
 * @Description: direct 预创建订单之后将创建订单的任务发送到mq，消费者收到消息创建订单
 */
@Configuration
public class SeckillOrderCreateMq {

    /**
     * 创建订单队列交换机
     */
    public final static String SECKILL_ORDER_CREATE_DIRECT_EXCHANGE = "seckill_order_create_direct_exchange";

    /**
     * 创建订单队列名称
     */
    public final static String SECKILL_ORDER_CREATE_QUEUE = "seckill_order_create_queue";

    /**
     * 路由键
     */
    private final static String SECKILL_ORDER_CREATE_ROUTING_KEY = "seckill_order.create";

    /**
     * 绑定键
     */
    public final static String SECKILL_ORDER_CREATE_BINDING_KEY = "seckill_order.create";

    /**
     * 声明创建订单队列
     *
     * @return
     */
    @Bean
    public Queue seckillOrderCreateQueue() {
        return new Queue(SECKILL_ORDER_CREATE_QUEUE);
    }

    /**
     * 声明创建订单direct交换器
     *
     * @return
     */
    @Bean
    public DirectExchange seckillOrderCreateExchange() {
        return new DirectExchange(SECKILL_ORDER_CREATE_DIRECT_EXCHANGE);
    }

    /**
     * 绑定创建订单队列到交换机
     *
     * @param seckillOrderCreateQueue
     * @param seckillOrderCreateExchange
     * @return
     */
    @Bean
    public Binding seckillOrderCreateBinding(Queue seckillOrderCreateQueue, DirectExchange seckillOrderCreateExchange) {
        return BindingBuilder.bind(seckillOrderCreateQueue).to(seckillOrderCreateExchange).with(SECKILL_ORDER_CREATE_ROUTING_KEY);
    }
}
