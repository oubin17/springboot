package com.ob.common.aspect.redisexception;

import java.lang.annotation.*;

/**
 * @Author: oubin
 * @Date: 2020/9/16 10:01
 * @Description: java.net.ConnectException: Connection refused: connect
 * 这里可以实现redis的异常捕获，做业务上的降级处理，但是对于请求而言还是返回一个异常。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface RedisConnectionException {
}
