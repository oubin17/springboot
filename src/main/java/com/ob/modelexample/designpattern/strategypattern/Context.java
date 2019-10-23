package com.ob.modelexample.designpattern.strategypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 11:38
 * @Description: 策略模式
 */
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void ContextInterface() {
        strategy.algorithmInterface();
    }
}
