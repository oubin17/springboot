package com.ob.business.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: oubin
 * @date: 2018/10/24 16:22
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/test")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        return "Hello World";
    }
}