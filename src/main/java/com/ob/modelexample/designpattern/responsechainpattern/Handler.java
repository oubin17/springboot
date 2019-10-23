package com.ob.modelexample.designpattern.responsechainpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 11:07
 * @Description:
 */
public abstract class Handler {

    protected Handler successor;

    public abstract void handleRequest(int request);

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
}
