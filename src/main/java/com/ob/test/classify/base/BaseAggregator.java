package com.ob.test.classify.base;

import java.util.Map;

/**
 * @Author: oubin
 * @Date: 2019/10/12 16:19
 * @Description:
 */
public abstract class BaseAggregator<T> implements Aggregative<T> {

    protected Map<String, String> query;

    public void beforeAggragate() {};

}
