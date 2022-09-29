package com.jvm.demo;

public class MaxTenuringThresholdDemo {

    public static void main(String[] args) {
        byte[] b1,b2,b3;                    // 定义变量
        b1 = new byte[1024 * 512];          // 分配 0.5MB 堆内存
        b2 = new byte[1024 * 1024 * 4];     // 分配 4MB 堆内存
        b3 = new byte[1024 * 1024 * 4];
        b3 = null;                          // 使用 b3 可以被回收
        b3 = new byte[1024 * 1024 * 4];     // 分配 4MB 堆内存
    }

}
