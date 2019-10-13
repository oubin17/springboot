package com.ob.modelexample.amqp.mqconfig.delayqueue;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * @Author: oubin
 * @Date: 2019/8/14 09:44
 * @Description:
 */
public class ExpirationMessagePostProcessor implements MessagePostProcessor {

    private final Long ttl;

    public ExpirationMessagePostProcessor(Long ttl) {
        this.ttl = ttl;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setExpiration(ttl.toString());
        return message;
    }
}
