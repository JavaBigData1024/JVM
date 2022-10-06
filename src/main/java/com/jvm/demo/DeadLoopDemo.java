package com.jvm.demo;

public class DeadLoopDemo {

    public static void main(String[] args) {
        DeadLoopDemo demo = new DeadLoopDemo();
        demo.deadLoop();
    }

    /**
     * 死循环示例
     */
    public void deadLoop() {
        while (true) {
            System.out.println("hello world");
        }
    }

}
