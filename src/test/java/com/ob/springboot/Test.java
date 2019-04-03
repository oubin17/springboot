package com.ob.springboot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.ob.business.domain.Student;
import com.ob.util.JsonUtil;

import java.util.List;


/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        List<Student> list = Lists.newArrayList();
        Student student = new Student();
        student.setName("oubin");
        student.setId("171913");
        Student student1 = new Student();
        student1.setName("ob1");
        student1.setId("123456");
        list.add(student);
        list.add(student1);
        String a = JsonUtil.toJson(list);

        List<Student> list1 = JsonUtil.jsonToBean(a, new TypeReference<List<Student>>() {});
        System.out.println(list1.get(1).getName());

    }
}
