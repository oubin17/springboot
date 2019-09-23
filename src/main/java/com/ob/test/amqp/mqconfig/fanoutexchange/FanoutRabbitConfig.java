package com.ob.test.amqp.mqconfig.fanoutexchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/8/13 11:54
 * @Description:
 */
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue aMessage() {
        return new Queue("q_fanout_A");
    }

    @Bean
    public Queue bMessage() {
        return new Queue("q_fanout_B");
    }

    @Bean
    public Queue cMessage() {
        return new Queue("q_fanout_C");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }

    @Bean
    public Binding bindingExchangeA(Queue aMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(aMessage).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeB(Queue bMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(bMessage).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeC(Queue cMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cMessage).to(fanoutExchange);
    }


}
