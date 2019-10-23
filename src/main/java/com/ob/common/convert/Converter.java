package com.ob.common.convert;


/**
 * @Author: oubin
 * @Date: 2019/10/10 14:52
 * @Description:
 */
public abstract class Converter<A, B> {

    /**
     * 正向转
     *
     * @param a
     * @return
     */
    protected abstract B doForward(A a);

    /**
     * 逆向转
     * @param b
     * @return
     */
    protected abstract A doBackward(B b);
}
