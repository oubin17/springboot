package com.ob.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: oubin
 * @Date: 2019/6/1 13:57
 * @Description:
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue generateQueue() {
        return new Queue("oqueue");
    }
}
