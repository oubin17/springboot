package com.ob.modelexample.designpattern.proxypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 15:26
 * @Description:
 */
public class Pursuit implements GiveGift {

    private SchoolGirl schoolGirl;

    public Pursuit(SchoolGirl schoolGirl) {
        this.schoolGirl = schoolGirl;
    }

    @Override
    public void giveDolls() {
        System.out.println("送洋娃娃");
    }

    @Override
    public void giveFlowers() {
        System.out.println("送花");
    }

    @Override
    public void giveChocolate() {
        System.out.println("送巧克力");
    }
}
