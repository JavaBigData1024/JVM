package com.jvm.demo;

public class DeadLockDemo {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public static void main(String[] args) {
        DeadLockDemo demo = new DeadLockDemo();
        demo.deadLock();
    }

    /**
     * 线程死锁示例
     */
    public void deadLock() {
        // 第一个线程，先占用lock1，然后再获取lock2
        new Thread(() -> {
            System.out.println("线程1开始");
            synchronized (lock1) {
                try {
                    System.out.println("线程1占用lock1");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("线程1发生了中断异常");
                }
                synchronized (lock2) {
                    System.out.println("线程1占用lock2");
                }
            }
            System.out.println("线程1结束");
        }).start();

        // 第二个线程，先占用lock2，然后再获取lock1
        new Thread(() -> {
            System.out.println("线程2开始");
            synchronized (lock2) {
                try {
                    System.out.println("线程2占用lock2");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("线程2发生了中断异常");
                }
                synchronized (lock1) {
                    System.out.println("线程2占用lock1");
                }
            }
            System.out.println("线程2结束");
        }).start();
    }

}
