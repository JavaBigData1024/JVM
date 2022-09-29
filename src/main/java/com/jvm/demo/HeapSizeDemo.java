package com.jvm.demo;

import java.util.ArrayList;
import java.util.List;

public class HeapSizeDemo {

    public static void main(String[] args) throws InterruptedException {
        List list = new ArrayList();
        while(true) {
            byte[] b = new byte[1024 * 1024];
            list.add(b);
            if (list.size() == 10) {
                list = new ArrayList();
                Thread.sleep(1);
            }
        }
    }

}
