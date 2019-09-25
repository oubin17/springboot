package com.ob.work.trade.messagequeue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/9/25 16:30
 * @Description:
 */
@Configuration
public class GoodsMqConfig {

    /**
     * 交换机名称
     */
    public final static String GOODS_TOPIC_EXCHANGE = "goods_topic_exchange";

    /**
     * 队列名称
     */
    public final static String GOODS_ORDER_REBUILD_QUEUE = "goods_order_rebuild_queue";

    /**
     * 路由键
     */
    private final static String GOODS_TOPIC_EXCHANGE_ROUTING_KEY = "topic.goods.rebuild.#";

    public final static String GOODS_ORDER_REBUILD_ROUTING_KEY = "topic.goods.rebuild";

    @Bean
    public Queue goodsOrderRebuildQueue() {
        return new Queue(GOODS_ORDER_REBUILD_QUEUE);
    }

    /**
     * 声明一个Topic类型的交换机
     *
     * @return
     */
    @Bean
    TopicExchange goodsTopicExchange() {
        return new TopicExchange(GOODS_TOPIC_EXCHANGE);
    }

    /**
     * 绑定队列到交换机
     *
     * @param goodsOrderRebuildQueue
     * @param goodsTopicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue goodsOrderRebuildQueue, TopicExchange goodsTopicExchange) {
        return BindingBuilder.bind(goodsOrderRebuildQueue).to(goodsTopicExchange).with(GOODS_TOPIC_EXCHANGE_ROUTING_KEY);
    }

}
