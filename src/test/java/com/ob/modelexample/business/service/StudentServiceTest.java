package com.ob.modelexample.business.service;

import com.ob.SpringbootApplication;
import com.ob.modelexample.business.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: oubin
 * @Date: 2019/4/16 09:04
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void test004() {
        Student student = studentService.get("2a690955-d584-4459-b2ba-c60148a53323");
        System.out.println(student.getCountStudent());
    }
}
