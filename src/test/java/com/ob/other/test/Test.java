package com.ob.other.test;

import com.google.common.collect.Lists;
import com.ob.business.domain.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

/**
 * @author: oubin
 * @date: 2019/4/2 17:42
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

        List<Student> list = Lists.newArrayList();

        Student student1 = new Student();
        student1.setName("a");
        student1.setVersion(1);
        Student student2 = new Student();
        student2.setName("b");
        Student student3 = new Student();
        student3.setName("a");
        student3.setVersion(3);
        list.add(student1);
        list.add(student2);
        list.add(student3);

        ArrayList<Student> collect = list.stream()
                .collect(Collectors.collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(Student::getName))),
                ArrayList::new));

        System.out.println(collect);


    }
}
