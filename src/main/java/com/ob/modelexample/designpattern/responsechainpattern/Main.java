package com.ob.modelexample.designpattern.responsechainpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/24 11:10
 * @Description: 责任链模式
 */
public class Main {

    public static void main(String[] args) {
        Handler h1 = new ConcreteHandler1();
        Handler h2 = new ConcreteHandler2();
        h1.setSuccessor(h2);
        int[] requests = {1,2,10,20};

        for (int i = 0; i < requests.length; i++) {
            h1.handleRequest(requests[i]);
        }
    }
}
