package com.jvm.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.LockSupport;

public class ThreadStatusDemo {
    public static void main(String[] args) throws InterruptedException {
        RunnableSocketThread thread1 = new RunnableSocketThread();
        thread1.start();

        RunnableThread thread2 = new RunnableThread();
        thread2.start();

        WaitingOnObjectThread thread3 = new WaitingOnObjectThread();
        thread3.start();

        TimedWaitingOnObjectThread thread4 = new TimedWaitingOnObjectThread();
        thread4.start();

        TimedWaitingSleepThread thread5 = new TimedWaitingSleepThread();
        thread5.start();

        TimedWaitingParkThread thread6 = new TimedWaitingParkThread();
        thread6.start();

        Thread.sleep(600000);
        LockSupport.unpark(thread5);
    }
}

/**
 * 模拟RUNNABLE状态的线程(等待Socket I/O时不消耗CPU)
 */
class RunnableSocketThread extends Thread {
    public RunnableSocketThread(){
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                // 堵塞式接受Socket
                Socket socket = serverSocket.accept();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        String line = reader.readLine();
                        System.out.println("从客户端来的信息：" + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    reader.close();
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 模拟RUNNABLE状态的线程(执行业务代码时消耗CPU)
 */
class RunnableThread extends Thread {
    public RunnableThread() {
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("hello");
        }
    }
}

/**
 * 模拟WAITING(on object monitor)状态的线程
 */
class WaitingOnObjectThread extends Thread {
    Object lock = new Object();

    public WaitingOnObjectThread() {
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 模拟TIMED_WAITING(on object monitor)状态的线程
 */
class TimedWaitingOnObjectThread extends Thread {
    Object lock = new Object();

    public TimedWaitingOnObjectThread() {
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                lock.wait(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 模拟TIMED_WAITING(sleeping)状态的线程
 */
class TimedWaitingSleepThread extends Thread {
    public TimedWaitingSleepThread() {
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 模拟TIMED_WAITING(paring)状态的线程
 */
class TimedWaitingParkThread extends Thread {
    public TimedWaitingParkThread() {
        this.setName(this.getClass().getName());
    }

    @Override
    public void run() {
        // 锁住60秒
        LockSupport.parkNanos(60000000000L);
    }
}
