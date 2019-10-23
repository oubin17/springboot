package com.ob.modelexample.designpattern.singletonpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 08:50
 * @Description: 单例模式
 */
public class Singleton {

    private static Singleton singleton;

    private Singleton() {

    }

    public static Singleton getInstance(){
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
