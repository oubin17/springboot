package com.ob.test.classify.base;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/12 16:08
 * @Description:
 */
public interface Aggregative<T> {

    T firstObject();

    T lastObject();

    List<T> fullObjects();
}
