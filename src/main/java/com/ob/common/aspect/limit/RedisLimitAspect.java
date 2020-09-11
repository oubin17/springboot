package com.ob.common.aspect.limit;

import com.google.common.collect.Lists;
import com.ob.common.context.SessionContext;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: oubin
 * @Date: 2019/12/17 15:54
 * @Description: 拦截器添加限流功能
 */
@Slf4j
@Aspect
@Component
public class RedisLimitAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.ob.common.aspect.limit.Limit)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        LimitType limitType = limit.limitType();
        String key = limit.key();
        if (StringUtils.isEmpty(key)) {
            if (limitType == LimitType.IP) {
                key = SessionContext.realIp();
            } else {
                key = method.getName();
            }
        }
        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = redisTemplate.execute(redisScript, Lists.newArrayList(key), limit.count(), limit.expire());
        if (null != count && count.intValue() <= limit.count()) {
            log.info("第{}次访问key为 {}，描述为 [{}] 的接口", count, key, limit.name());
            return joinPoint.proceed();
        } else {
            throw new BizException(ErrorCode.REQUEST_TIME_LIMIT);
        }

    }

    /**
     * 限流脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }
}
