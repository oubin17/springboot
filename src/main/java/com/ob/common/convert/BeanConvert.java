package com.ob.common.convert;

/**
 * @Author: oubin
 * @Date: 2019/10/8 16:52
 * @Description:
 */
public interface BeanConvert<S, T> {

    /**
     * 将S对象转成T对象
     *
     * @param s
     * @return
     */
    T convert(S s);
}
