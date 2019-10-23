package com.ob.modelexample.designpattern.strategypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 11:11
 * @Description:
 */
public class CashNormal extends CashSuper {

    @Override
    public double acceptCash(double money) {
        return money;
    }
}
