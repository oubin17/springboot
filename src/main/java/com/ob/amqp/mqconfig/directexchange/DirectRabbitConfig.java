package com.ob.amqp.mqconfig.directexchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/8/13 14:45
 * @Description:
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue directQueue1() {
        return new Queue("q_direct_A");
    }

    @Bean
    public Queue directQueue2() {
        return new Queue("q_direct_B");
    }

    @Bean
    public Queue directQueue3() {
        return new Queue("q_direct_C");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }

    @Bean
    public Binding bindingExchange1(Queue directQueue1, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue1).to(directExchange).with("direct_exchange");
    }

    @Bean
    public Binding bindingExchange2(Queue directQueue2, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue2).to(directExchange).with("direct_exchange");
    }

    @Bean
    public Binding bindingExchange3(Queue directQueue3, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue3).to(directExchange).with("direct_exchange_no");
    }
}
