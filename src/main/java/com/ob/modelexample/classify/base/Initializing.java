package com.ob.modelexample.classify.base;

/**
 * @Author: oubin
 * @Date: 2019/10/12 14:42
 * @Description:
 */
public interface Initializing {

    /**
     * 初始化数据需要调用的接口
     *
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
