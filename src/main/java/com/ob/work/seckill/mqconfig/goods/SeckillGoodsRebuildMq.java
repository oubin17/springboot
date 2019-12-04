package com.ob.work.seckill.mqconfig.goods;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/12/3 14:32
 * @Description:
 */
@Configuration
public class SeckillGoodsRebuildMq {

    /**
     * 重建队列交换机
     */
    public final static String SECKILL_GOODS_REBUILD_TOPIC_EXCHANGE = "seckill_goods_rebuild_topic_exchange";

    /**
     * 重建队列名称
     */
    public final static String SECKILL_GOODS_REBUILD_QUEUE = "seckill_goods_rebuild_queue";

    /**
     * 路由键
     */
    private final static String SECKILL_GOODS_REBUILD_ROUTING_KEY = "seckill_goods.rebuild.#";

    /**
     * 绑定键
     */
    public final static String SECKILL_GOODS_REBUILD_BINDING_KEY = "seckill_goods.rebuild.all";

    /**
     * 声明重建队列
     *
     * @return
     */
    @Bean
    public Queue seckillGoodsRebuildQueue() {
        return new Queue(SECKILL_GOODS_REBUILD_QUEUE);
    }

    /**
     * 声明重建topic交换器
     *
     * @return
     */
    @Bean
    public TopicExchange seckillGoodsRebuildExchange() {
        return new TopicExchange(SECKILL_GOODS_REBUILD_TOPIC_EXCHANGE);
    }

    /**
     * 绑定重建队列到交换机
     *
     * @param seckillGoodsRebuildQueue
     * @param seckillGoodsRebuildExchange
     * @return
     */
    @Bean
    public Binding seckillGoodsRebuildBinding(Queue seckillGoodsRebuildQueue, TopicExchange seckillGoodsRebuildExchange) {
        return BindingBuilder.bind(seckillGoodsRebuildQueue).to(seckillGoodsRebuildExchange).with(SECKILL_GOODS_REBUILD_ROUTING_KEY);
    }
}
