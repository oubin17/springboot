package com.ob.modelexample.designpattern.factoryfuncpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 17:34
 * @Description:
 */
public class VolunteerFactory implements IFactory {

    @Override
    public LeiFeng createLeiFeng() {
        return new Volunteer();
    }
}
