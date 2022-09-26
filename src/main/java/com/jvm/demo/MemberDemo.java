package com.jvm.demo;

import java.util.HashMap;
import java.util.Map;

class Member {
    String memberNo;
    String memberName;

    public Member(String memberNo, String memberName) {
        this.memberNo = memberNo;
        this.memberName = memberName;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}

public class MemberDemo {

    // 方法外定义的静态全局容器对象
    private static Map<String, Member> memberMap1 = new HashMap<>();
    // 方法外定义的非静态全局容器对象
    private Map<String, Member> memberMap2 = new HashMap<>();

    public void fun1() {
        // 方法内定义的对象m1，没有被外部对象引用，所以该方法执行结束，该对象m1就会成为”垃圾“对象，等待虚拟机回收
        Member m1 = new Member("1001", "张三");
    }

    public void fun2() {
        // 方法内定义的对象m2，被方法外定义的静态全局容器对象引用，该方法执行结束，该对象m2也不会成为”垃圾“对象
        // 只有执行了fun3()，该对象m2才会成为”垃圾对象“，等待虚拟机回收
        Member m2 = new Member("1002", "李四");
        memberMap1.put("member", m2);
    }

    public void fun3() {
        // 从静态全局容器对象中移除对在fun2()中定义的对象m2的引用
        memberMap1.remove("member");
    }

    public void fun4() {
        // 方法内定义的对象m3，被方法外定义的非静态全局容器对象引用
        Member m3 = new Member("1003", "王五");
        memberMap2.put(m3.getMemberNo(), m3);
        // 使memberMap2所指向的那个HashMap对象以及该HashMap对象中引用的对象m3成为孤岛
        memberMap2 = null;
    }

    public static void main(String[] args) {
        MemberDemo memberDemo = new MemberDemo();
        memberDemo.fun1();
        memberDemo.fun2();
        memberDemo.fun3();
        memberDemo.fun4();
    }
}
