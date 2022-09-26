package com.jvm.demo;

import java.util.ArrayList;
import java.util.List;

class User {
    String userNo;
    String userName;

    public User(String userNo, String userName) {
        this.userNo = userNo;
        this.userName = userName;
    }

}

public class UserDemo {

    // 全局变量
    private List<User> userList = new ArrayList<>();

    /**
     * 会发生内存泄漏的示例
     */
    public void fun1() {
        // 局部变量user1
        User user1 = new User("1001", "张三");
        // 全局变量引用局部变量user1所引用的对象
        userList.add(user1);
        // 把局部变量user1设置为null，但是全局变量仍然在引用局部变量user1原来所引用的那个对象，所以这里会发生内存泄漏
        user1 = null;
    }

    /**
     * 不会发生内存泄漏的示例
     */
    public void fun2() {
        // 局部变量user2
        User user2 = new User("1001", "张三");
        // 全局变量引用局部变量user1所引用的对象
        userList.add(user2);
        // 从全局变量中移除对局部变量user2原来所引用的那个对象的引用，虽然局部变量user2仍然在引用那个对象，但是fun2()执行完成，局部变量use2就变成了”垃圾对象“，所以这里不会发生内存
        userList.remove(user2);
    }

}
