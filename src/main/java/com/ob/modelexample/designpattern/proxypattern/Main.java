package com.ob.modelexample.designpattern.proxypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 15:28
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        SchoolGirl schoolGirl = new SchoolGirl();
        schoolGirl.setName("花花");
        Proxy proxy = new Proxy(schoolGirl);
        proxy.giveDolls();

    }
}
