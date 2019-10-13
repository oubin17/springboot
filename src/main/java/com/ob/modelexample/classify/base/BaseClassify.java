package com.ob.modelexample.classify.base;

import com.ob.modelexample.classify.aggregate.BaseAggregator;
import lombok.Data;

/**
 * @Author: oubin
 * @Date: 2019/10/12 15:26
 * @Description:
 */
@Data
public abstract class BaseClassify<T> {

    protected String domainName;

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
