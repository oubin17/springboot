package com.ob.modelexample.amqp.controller;

import com.ob.modelexample.amqp.service.RabbitConfirmService;
import com.ob.modelexample.amqp.service.RabbitService;
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
    private RabbitService rabbitService;

    @Autowired
    private RabbitConfirmService confirmService;

    @RequestMapping(value = "/{message}", method = RequestMethod.GET)
    public void send(@PathVariable(value = "message") String message) {
        rabbitService.simpleQueue(message);
    }

    @RequestMapping(value = "/topic/{message}", method = RequestMethod.GET)
    public void topicSend(@PathVariable(value = "message") String message) {
        rabbitService.topicQueue1(message);
    }


    /**
     * 消息确认机制和消息路由不可达的return处理
     *
     * @param message
     */
    @RequestMapping(value = "/confirm/{message}", method = RequestMethod.GET)
    public void msgConfirm(@PathVariable(value = "message") String message) {
        confirmService.sendMsg(message);
    }
}
