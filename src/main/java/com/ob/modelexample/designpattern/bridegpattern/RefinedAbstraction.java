package com.ob.modelexample.designpattern.bridegpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 09:23
 * @Description:
 */
public class RefinedAbstraction extends Abstraction {

    @Override
    public void operation() {
        implementor.operation();
    }
}
