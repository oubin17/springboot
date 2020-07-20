package com.ob.other.jvmtest;

/**
 * @Author: oubin
 * @Date: 2020/7/20 08:35
 * @Description:
 */
public class JvmTest {

    public static void main(String[] args) {
        DeadLock d1 = new DeadLock(true);
        DeadLock d2 = new DeadLock(false);
        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);
        t1.start();
        t2.start();

    }
}

class MyLock{
    public static Object obj1 = new Object();
    public static Object obj2 = new Object();
}
class DeadLock implements Runnable {
    private boolean flag;
    DeadLock (boolean flag) {
        this.flag = flag;
    }
    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (MyLock.obj1) {
                    System.out.println(Thread.currentThread().getName() + "----获得object1锁");
                    synchronized (MyLock.obj2) {
                        System.out.println(Thread.currentThread().getName() + "----获得object2锁");

                    }
                }
            }
        } else {
            while (true) {
                synchronized (MyLock.obj2) {
                    System.out.println(Thread.currentThread().getName() + "----获得object2锁");
                    synchronized (MyLock.obj1) {
                        System.out.println(Thread.currentThread().getName() + "----获得object1锁");
                    }
                }
            }
        }
    }
}
