package com.ob.modelexample.designpattern.responsechainpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 11:09
 * @Description:
 */
public class ConcreteHandler2 extends Handler {

    @Override
    public void handleRequest(int request) {
        if (request >= 10 && request < 20) {
            System.out.println("handler2处理请求");
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
