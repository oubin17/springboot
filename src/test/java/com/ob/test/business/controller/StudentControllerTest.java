package com.ob.test.business.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ob.SpringbootApplication;
import com.ob.test.business.dto.StudentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: oubin
 * @Date: 2019/4/16 10:42
 * @Description:
 * 1.RunWith表明是个测试类,Spring boot 1.4之前使用SpringJUnit4ClassRunner注解
 * 2.SpringBootTest表明如何加载测试上下文
 * 3.WebAppConfiguration是一个web模拟请求
 * 4.transactional添加事务支持,在需要回滚的测试方法添加@Rollback注解
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration
@Transactional
public class StudentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * get请求测试
     *
     * @throws Exception
     */
    @Test
    public void test001() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v0.1/student/2a690955-d584-4459-b2ba-c60148a53323")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    /**
     * post请求测试
     *
     * @throws Exception
     */
    @Rollback
    @Test
    public void test002() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v0.1/student")
                .param("name", "studentTest")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void test003() throws Exception {
        StudentDto dto = new StudentDto();
        dto.setName("studentName");
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(dto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/v0.1/student/actions/update/2a690955-d584-4459-b2ba-c60148a53323")
                .content(string)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

    }
}
