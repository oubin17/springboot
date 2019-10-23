package com.ob.modelexample.designpattern.factorypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 10:27
 * @Description:
 */
public class OperationAdd extends Operation {

    public OperationAdd() {

    }

    @Override
    public double getResult() {
        double result;
        result = getNumberA() + getNumberB();
        return result;
    }
}
