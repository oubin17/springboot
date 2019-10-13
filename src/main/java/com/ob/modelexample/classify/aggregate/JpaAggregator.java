package com.ob.modelexample.classify.aggregate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/13 10:21
 * @Description:
 */
@Service
@Scope("prototype")
public class JpaAggregator<T> extends BaseAggregator<T> {

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
}
