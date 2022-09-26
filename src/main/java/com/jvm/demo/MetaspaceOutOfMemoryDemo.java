package com.jvm.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示非堆内存溢出
 * 运行时添加JVM参数： -XX:MetaspaceSize=16M -XX:MaxMetaspaceSize=16M
 */
public class MetaspaceOutOfMemoryDemo {

    private List<Class<?>> list = new ArrayList<>();

    public void outOfMemory() throws ClassNotFoundException {
        while (true) {
            Class<?> clazz = Class.forName("java.lang.String");
            list.add(clazz);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MetaspaceOutOfMemoryDemo demo = new MetaspaceOutOfMemoryDemo();
        demo.outOfMemory();
    }

}
