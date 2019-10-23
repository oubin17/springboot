package com.ob.modelexample.designpattern.factoryfuncpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 17:36
 * @Description:  工厂方法模式
 */
public class Main {

    public static void main(String[] args) {
        IFactory iFactory = new UndergraduateFactory();
        LeiFeng leiFeng = iFactory.createLeiFeng();

        IFactory iFactory1 = new VolunteerFactory();
        LeiFeng leiFeng1 = iFactory1.createLeiFeng();
    }
}
