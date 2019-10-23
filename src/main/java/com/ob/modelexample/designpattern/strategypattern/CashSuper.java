package com.ob.modelexample.designpattern.strategypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 11:09
 * @Description:
 */
public abstract class CashSuper {

    /**
     * 现金收取超类的抽象方法，收取现金，参数为原价，返回为当前价
     *
     * @param money
     * @return
     */
    public abstract double acceptCash(double money);
}
