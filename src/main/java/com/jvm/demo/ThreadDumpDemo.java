package com.jvm.demo;

public class ThreadDumpDemo {

    Object obj1 = new Object();
    Object obj2 = new Object();

    public static void main(String[] args) {
        ThreadDumpDemo demo = new ThreadDumpDemo();
        demo.fun1();
    }

    public void fun1() {
        synchronized (obj1) {
            fun2();
        }
    }

    public void fun2() {
        synchronized (obj2) {
            // 为了演示需要，该函数永不退出
            while(true) {
                System.out.println("hello");
            }
        }
    }

}
