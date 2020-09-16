package com.ob.common.aspect.redisexception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: oubin
 * @Date: 2020/9/16 10:02
 * @Description:
 */
@Slf4j
@Aspect
@Component
public class RedisConnectionExceptionAspect {

    @Pointcut("@annotation(com.ob.common.aspect.redisexception.RedisConnectionException)")
    public void pointcut() {

    }

    @AfterThrowing("pointcut()")
    public int afterThrowing(JoinPoint joinPoint) {
       return 1;
    }
}
