package com.kowksiuwang.everythingaboutandroid.coroutine;

import org.junit.Test;

/**
 * Created by kwoksiuwang on 12/17/20!!!
 */
public class WaitAndNotifyTestJava {
    Object lock = new Object();

    @Test
    public void test() {
        new Thread(() -> {
            try {
                synchronized (lock) {
                    System.out.println("thread 1 hold lock " + System.currentTimeMillis());
                    Thread.sleep(100);
                    System.out.println("thread 1 wait " + System.currentTimeMillis());
                    lock.wait();
                    System.out.println("thread 1 wakeup " + System.currentTimeMillis());
//                    lock.notify();
                    Thread.sleep(100);
                    System.out.println("thread 1 stop " + System.currentTimeMillis());
                    lock.notify();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                synchronized (lock){
                    System.out.println("thread 2 running " + System.currentTimeMillis());
                    Thread.sleep(100);
                    System.out.println("thread 2 notify() 1 " + System.currentTimeMillis());
                    lock.notify();
                    lock.wait(100);
                    System.out.println("thread 2 notify() 2 " + System.currentTimeMillis());
                    Thread.sleep(100);
                    System.out.println("thread 2 stop " + System.currentTimeMillis());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            Thread.sleep(1000);
//            lock.notifyAll();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
