package com.ob.test.classify.base;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/12 15:35
 * @Description:
 */
@ClassifyName(name = "StudentClassify")
public class StudentClassify<T> extends BaseClassify<T> implements Aggregative<T>, Initializing {

    @Override
    public T firstObject() {
        return null;
    }

    @Override
    public T lastObject() {
        return null;
    }

    @Override
    public List<T> fullObjects() {
        return null;
    }

    @Override
    protected void generateDomain() {

    }

    @Override
    protected void persistDomain() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
