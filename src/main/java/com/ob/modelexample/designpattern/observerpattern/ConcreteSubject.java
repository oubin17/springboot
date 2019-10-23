package com.ob.modelexample.designpattern.observerpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/23 09:46
 * @Description:
 */
public class ConcreteSubject extends Subject {

    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }
}
