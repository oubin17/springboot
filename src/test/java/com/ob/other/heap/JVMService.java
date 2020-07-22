package com.ob.other.heap;

import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2020/7/21 14:27
 * @Description:
 */
@Service
public class JVMService {

    public static Object obj1 = new Object();
    public static Object obj2 = new Object();

    public void deadLock() {
        DeadLock d1 = new DeadLock(true);
        DeadLock d2 = new DeadLock(false);
        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);
        t1.start();
        t2.start();
    }
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
                synchronized (JVMService.obj1) {
                    System.out.println(Thread.currentThread().getName() + "----获得object1锁");
                    synchronized (JVMService.obj2) {
                        System.out.println(Thread.currentThread().getName() + "----获得object2锁");

                    }
                }
            }
        } else {
            while (true) {
                synchronized (JVMService.obj2) {
                    System.out.println(Thread.currentThread().getName() + "----获得object2锁");
                    synchronized (JVMService.obj1) {
                        System.out.println(Thread.currentThread().getName() + "----获得object1锁");
                    }
                }
            }
        }
    }
}