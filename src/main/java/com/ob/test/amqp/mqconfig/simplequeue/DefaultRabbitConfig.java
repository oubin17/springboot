package com.ob.test.amqp.mqconfig.simplequeue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/6/1 13:57
 * @Description:
 */
@Configuration
public class DefaultRabbitConfig {

    @Bean
    public Queue generateQueue() {
        return new Queue("o_queue");
    }
}
