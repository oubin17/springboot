package com.ob.business.controller;

import com.google.common.collect.Lists;
import com.ob.business.domain.Student;
import com.ob.business.service.StudentService;
import com.ob.common.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: oubin
 * @date: 2019/3/28 14:15
 * @Description:
 */
@RestController
@RequestMapping(value = "/v0.1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam(name = "name") String name) {
        studentService.save(name);

    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student get(@PathVariable(name = "id") String id) {
        return studentService.get(id);

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
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
