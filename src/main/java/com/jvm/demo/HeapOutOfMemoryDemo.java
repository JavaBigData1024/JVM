package com.jvm.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 演示堆内存溢出
 * 运行时添加JVM参数：-Xmx16M -Xms16M
 */
public class HeapOutOfMemoryDemo {

    private List<UUID> list = new ArrayList<>();

    public void outOfMemory() {
        while(true) {
            list.add(UUID.randomUUID());
        }
    }

    public static void main(String[] args) {
        HeapOutOfMemoryDemo demo = new HeapOutOfMemoryDemo();
        demo.outOfMemory();
    }

}
