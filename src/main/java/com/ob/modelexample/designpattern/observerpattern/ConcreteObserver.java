package com.ob.modelexample.designpattern.observerpattern;

/**
 * @Author: oubin
 * @Date: 2019/5/23 09:48
 * @Description:
 */
public class ConcreteObserver extends Observer {

    public String name;

    private String observerState;

    private ConcreteSubject subject;

    public ConcreteObserver(ConcreteSubject subject, String name) {
        this.subject = subject;
        this.name = name;
    }


    @Override
    public void update() {
        observerState = subject.getSubjectState();
        System.out.println("观察者的新状态是");
    }

    public ConcreteSubject getSubject() {
        return subject;
    }

    public void setSubject(ConcreteSubject subject) {
        this.subject = subject;
    }
}
