package com.ob.modelexample.designpattern.factorypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 10:24
 * @Description:
 */
public class Operation {

    private double numberA = 0L;
    private double numberB = 0L;

    public double getResult() {
        double result = 0;
        return result;
    }

    public double getNumberA() {
        return numberA;
    }

    public void setNumberA(double numberA) {
        this.numberA = numberA;
    }

    public double getNumberB() {
        return numberB;
    }

    public void setNumberB(double numberB) {
        this.numberB = numberB;
    }
}
