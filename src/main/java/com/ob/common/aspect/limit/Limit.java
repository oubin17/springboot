package com.ob.common.aspect.limit;

import java.lang.annotation.*;

/**
 * @Author: oubin
 * @Date: 2019/12/17 15:57
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface Limit {

    /**
     * 限流key
     *
     * @return
     */
    String key() default "";

    /**
     * 允许最大数量
     *
     * @return
     */
    int count() default 0;

    /**
     * key过期时间:单位秒
     *
     * @return
     */
    int expire() default 0;

    /**
     * 限流类型
     *
     * @return
     */
    LimitType limitType() default LimitType.KEY;

    /**
     * 限流描述
     *
     * @return
     */
    String name() default "";
}
