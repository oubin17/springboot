package com.ob.modelexample.designpattern.proxypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 15:27
 * @Description: 代理模式
 */
public class Proxy implements GiveGift {

    private Pursuit pursuit;
    public Proxy(SchoolGirl schoolGirl) {
        pursuit = new Pursuit(schoolGirl);
    }

    @Override
    public void giveDolls() {
        pursuit.giveDolls();
    }

    @Override
    public void giveFlowers() {
        pursuit.giveFlowers();

    }

    @Override
    public void giveChocolate() {
        pursuit.giveChocolate();

    }

    public Pursuit getPursuit() {
        return pursuit;
    }

    public void setPursuit(Pursuit pursuit) {
        this.pursuit = pursuit;
    }
}
