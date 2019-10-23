package com.ob.modelexample.designpattern.bridegpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 09:22
 * @Description:
 */
public class Abstraction {

    protected Implementor implementor;

    public void operation() {
        implementor.operation();
    }

    public Implementor getImplementor() {
        return implementor;
    }

    public void setImplementor(Implementor implementor) {
        this.implementor = implementor;
    }
}
