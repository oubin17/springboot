package com.ob.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ob.SpringbootApplication;
import com.ob.business.domain.Student;
import com.ob.business.dto.StudentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
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
