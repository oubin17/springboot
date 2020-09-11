package com.ob.common.aspect.datacompensation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2020/9/11 11:15
 * @Description:
 */
@Slf4j
@Aspect
@Component
public class RedisDataCompensationAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.ob.common.aspect.datacompensation.DataCompensation)")
    public void pointcut() {

    }

    @AfterThrowing("pointcut()")
    public void around(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String key = String.valueOf(args[0]);
        redisTemplate.opsForList().leftPush(key, 1);
    }
}
