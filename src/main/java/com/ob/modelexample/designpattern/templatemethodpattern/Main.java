package com.ob.modelexample.designpattern.templatemethodpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 18:45
 * @Description: 模板方法模式
 */
public class Main {

    public static void main(String[] args) {
        AbstractClass abstractClass;
        abstractClass = new ConcreteClassA();
        abstractClass.templateMethod();
        abstractClass = new ConcreteClassB();
        abstractClass.templateMethod();
    }
}
