package com.ob.test.classify.aggregate;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/12 16:08
 * @Description:
 */
public interface Aggregative<T> {

    /**
     * 获取第一个对象
     *
     * @return
     */
    T firstObject();

    /**
     * 获取最后一个对象
     *
     * @return
     */
    T lastObject();

    /**
     * 获取所有对象
     *
     * @return
     */
    List<T> fullObjects();
}
