package com.ob.test.controller;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2020/8/19 16:27
 * @Description:
 */
@RestController
public class TestClass {


    @GetMapping("/new_object")
    public void newObject() {
        List<Object> list = Lists.newArrayList();
        while (true) {
            list.add(new Object());
        }
    }


    @GetMapping("/thread")
    public void thread() {
        while (true) {
            new Thread().start();
        }
    }
}
