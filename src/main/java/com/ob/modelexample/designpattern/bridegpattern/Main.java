package com.ob.modelexample.designpattern.bridegpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 09:24
 * @Description: 桥接模式
 */
public class Main {

    public static void main(String[] args) {

        Abstraction abstraction = new RefinedAbstraction();
        abstraction.setImplementor(new ConcreteImplementorA());
        abstraction.operation();
        abstraction.setImplementor(new ConcreteImplementorB());
        abstraction.operation();
    }
}
