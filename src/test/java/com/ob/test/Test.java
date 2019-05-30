package com.ob.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ob.business.domain.Student;
import com.ob.util.JsonUtil;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static javafx.scene.input.KeyCode.L;


/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {
    public static int a = 1;

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList();
        list.add(a);
        a = 2;
        list.add(a);
        System.out.println(list);
//        List<Student> list = Lists.newArrayList();
//        Student student = new Student();
//        student.setName("oubin");
//        student.setId("171913");
//        Student student1 = new Student();
//        student1.setName("ob1");
//        student1.setId("123456");
//        list.add(student);
//        list.add(student1);
//        String a = JsonUtil.toJson(list);
//
//        List<Student> list1 = JsonUtil.jsonToBean(a, new TypeReference<List<Student>>() {});
//        System.out.println(list1.get(1).getName());
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("QQQ-");
//        stringBuilder.append("AAA");
//        System.out.println(stringBuilder.toString());
//        stringBuilder.delete(4, 7);
//        System.out.println(stringBuilder);
//        stringBuilder.append("BBB");
//        System.out.println(stringBuilder);
//        stringBuilder.delete(0, stringBuilder.length());
//        System.out.println("abc" + stringBuilder.toString());
//        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
//        System.out.println(date);
//        calendar.setTime(date);
//        calendar.add(Calendar.HOUR_OF_DAY, 8);
//        System.out.println(calendar.getTime());
//        long time = 1557905018000L;
//        Date date = new Date(time);
//        System.out.println(date);
//        Map<String, String> map = new HashMap<>();



    }
}
