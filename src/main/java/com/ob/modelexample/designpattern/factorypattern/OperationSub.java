package com.ob.modelexample.designpattern.factorypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 10:29
 * @Description:
 */
public class OperationSub extends Operation {

    public OperationSub() {

    }
    @Override
    public double getResult() {
        double result;
        result = getNumberA() - getNumberB();
        return result;
    }
}
