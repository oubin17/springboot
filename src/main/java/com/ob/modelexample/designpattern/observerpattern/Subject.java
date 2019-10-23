package com.ob.modelexample.designpattern.observerpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/5/23 09:46
 * @Description:
 */
public abstract class Subject {

    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyed() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
