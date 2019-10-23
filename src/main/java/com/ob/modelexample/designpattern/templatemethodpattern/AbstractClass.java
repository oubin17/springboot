package com.ob.modelexample.designpattern.templatemethodpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 18:40
 * @Description:
 */
public abstract class AbstractClass {

    public abstract void method1();

    public abstract void method2();

    public void templateMethod() {
        method1();
        method2();
        System.out.println("模板方法模式");
    }
}
