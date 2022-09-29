package com.jvm.demo;

public class PutInEdenByXmnDemo {

    public static void main(String[] args) {
        byte[] b1,b2,b3,b4;             // 定义变量
        b1 = new byte[1024 * 1024];     // 分配 1MB 堆内存，考察堆内存的使用情况
        b2 = new byte[1024 * 1024];
        b3 = new byte[1024 * 1024];
        b4 = new byte[1024 * 1024];
    }

}
