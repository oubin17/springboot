package com.ob.redis;

import com.ob.SpringbootApplication;
import com.ob.business.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oubin
 * @Date: 2019/7/6 09:48
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration
public class RedisServiceTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test001() {
        Student student = new Student();
        student.setName("ob");
        student.setCountStudent(100);
        String key = "stuName";
        redisTemplate.opsForValue().set(key, student, 10, TimeUnit.SECONDS);
        Student studentRedis = (Student) redisTemplate.opsForValue().get(key);
        System.out.println(studentRedis.getName());
    }
}
