package com.ob.other.test;

/**
 * @Author: oubin
 * @Date: 2020/8/21 09:00
 * @Description:
 */
public class Test1 {

    public static void main(String[] args) {

        Object o = new Object();


        new Thread(() -> {
            synchronized (o) {
                System.out.println("abc");
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (o) {
                System.out.println("abc");
                o.notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
