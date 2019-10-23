package com.ob.modelexample.designpattern.responsechainpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 11:08
 * @Description:
 */
public class ConcreteHandler1 extends Handler {

    @Override
    public void handleRequest(int request) {
        if (request >= 0 && request < 10) {
            System.out.println("handle1处理请求");
        } else if (successor != null) {
            successor.handleRequest(request);
        }

    }
}
