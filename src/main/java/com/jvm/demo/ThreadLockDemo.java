package com.jvm.demo;

public class ThreadLockDemo {

    public static void main(String[] args) {
        Object lock = new Object();

        LockedThread lockedThread = new LockedThread(lock);
        lockedThread.start();

        WaitingToThread waitingToThread = new WaitingToThread(lock);
        waitingToThread.start();

        WaitingOnThread waitingOnThread = new WaitingOnThread();
        waitingOnThread.start();
    }

}

/**
 * 模拟locked锁的线程
 */
class LockedThread extends Thread {

    Object lock;

    public LockedThread(Object lock) {
        this.lock = lock;
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                // sleep()不释放锁
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

/**
 * 模拟waiting to lock锁的线程
 */
class WaitingToThread extends Thread {

    Object lock;

    public WaitingToThread(Object lock) {
        this.lock = lock;
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                // sleep()不释放锁
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

/**
 * 模拟waiting on锁的线程
 */
class WaitingOnThread extends Thread {

    Object lock = new Object();

    public WaitingOnThread() {
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                // wait()释放锁
                lock.wait(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
