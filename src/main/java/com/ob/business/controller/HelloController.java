package com.ob.business.controller;

import com.google.common.collect.Lists;
import com.ob.business.domain.Student;
import com.ob.common.Items;
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
}
