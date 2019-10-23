package com.ob.modelexample.designpattern.strategypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 11:13
 * @Description:
 */
public class CashRebate extends CashSuper {

    private double moneyRebate;

    public CashRebate(double moneyRebate) {
        this.moneyRebate = moneyRebate;
    }
    @Override
    public double acceptCash(double money) {

        return moneyRebate * money;
    }
}
