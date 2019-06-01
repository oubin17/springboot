package com.ob.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: oubin
 * @Date: 2019/6/1 14:03
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/rabbit")
public class RabbitController {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    @RequestMapping(value = "/{message}", method = RequestMethod.GET)
    public void send(@PathVariable(value = "message") String message) {
        rabbitmqTemplate.convertAndSend("oqueue", message);
    }
}
