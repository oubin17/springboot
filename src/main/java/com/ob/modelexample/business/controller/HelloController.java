package com.ob.modelexample.business.controller;

import com.google.common.collect.Lists;
import com.ob.modelexample.business.domain.Student;
import com.ob.modelexample.business.service.HelloService;
import com.ob.common.response.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: oubin
 * @date: 2018/10/24 16:22
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/test")
public class HelloController {

    @Value("${author.id}")
    private String authorId;

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        return "Hello World" + authorId;
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public Items<Student> test() {
        List<Student> list = Lists.newArrayList();
        Student student = new Student();
        Student student1 = new Student();
        student.setName("a");
        student1.setName("b");
        list.add(student);
        list.add(student1);
        return Items.of(list);
    }

    @RequestMapping(value = "/proxy", method = RequestMethod.GET)
    public void proxyTest() {
        helloService.proxyText();
    }
}
