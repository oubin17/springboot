package com.ob.modelexample.designpattern.factoryfuncpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 17:28
 * @Description:
 */
public class UndergraduateFactory implements IFactory {

    @Override
    public LeiFeng createLeiFeng() {
        return new UnderGraduate();
    }
}
