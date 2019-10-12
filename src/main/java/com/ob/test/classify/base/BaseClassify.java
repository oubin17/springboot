package com.ob.test.classify.base;

/**
 * @Author: oubin
 * @Date: 2019/10/12 15:26
 * @Description:
 */
public abstract class BaseClassify<T> {

    private String domainName;

    private BaseAggregator<T> baseAggregator;

    /**
     * 抽象方法1
     */
    protected abstract void generateDomain();

    /**
     * 抽象方法2
     */
    protected abstract void persistDomain();

    public void totalFun() {
        generateDomain();
        persistDomain();
    }
}
