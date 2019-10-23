package com.ob.modelexample.designpattern.bridegpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 09:20
 * @Description:
 */
public class ConcreteImplementorA extends Implementor {

    @Override
    public void operation() {
        System.out.println("具体实现A的方法执行");
    }
}
