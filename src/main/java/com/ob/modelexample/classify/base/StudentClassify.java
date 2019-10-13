package com.ob.modelexample.classify.base;

import com.ob.modelexample.classify.aggregate.Aggregative;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/12 15:35
 * @Description:
 */
@Slf4j
@ClassifyName(name = "StudentClassify")
public class StudentClassify<T> extends BaseClassify<T> implements Aggregative<T>, Initializing {

    @Override
    public T firstObject() {
        return this.getBaseAggregator().firstObject();
    }

    @Override
    public T lastObject() {
        return this.getBaseAggregator().lastObject();
    }

    @Override
    public List<T> fullObjects() {
        return this.getBaseAggregator().fullObjects();
    }

    @Override
    protected void generateDomain() {

    }

    @Override
    protected void persistDomain() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("调用初始化方法成功...");
    }
}
