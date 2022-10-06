package com.jvm.demo;

import com.sun.btrace.org.objectweb.asm.ClassWriter;
import com.sun.btrace.org.objectweb.asm.MethodVisitor;
import com.sun.btrace.org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示非堆内存溢出
 * 运行时添加JVM参数： -XX:MetaspaceSize=16M -XX:MaxMetaspaceSize=16M
 */
public class MetaspaceOutOfMemoryDemo {

    public void outOfMemory() {
        List<Class<?>> classList = new ArrayList<>();
        for (int i = 1; i < 10000000; ++i) {
            String className = String.format("Class%s", i);
            classList.add(CustomClassLoader.createClass(className));
            if (i % 100 == 0) {
                System.out.println("Total Class: " + i);
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MetaspaceOutOfMemoryDemo demo = new MetaspaceOutOfMemoryDemo();
        demo.outOfMemory();
    }

}

class CustomClassLoader extends ClassLoader {

    /**
     * 通过btrace工具动态的创建并加载类，需要在pom文件中添加以下maven依赖：
     * <dependency>
     *     <groupId>com.sun.tools.btrace</groupId>
     *     <artifactId>btrace-agent</artifactId>
     *     <version>1.2.3</version>
     * </dependency>
     */
    public static Class<?> createClass(String className) {
        ClassWriter classWriter = new ClassWriter(0);
        // 定义一个类名称为Class{i}，它的访问域为public，父类为java.lang.Object，不实现任何接口
        classWriter.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);
        // 定义构造函数<init>方法
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        // 第一个指令为加载this
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        // 第二个指令为调用父类Object的构造函数
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        // 第三条指令为return
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
        byte[] classDataBytes = classWriter.toByteArray();
        // 定义类
        CustomClassLoader customClassLoader = new CustomClassLoader();
        return customClassLoader.defineClass(className, classDataBytes, 0, classDataBytes.length);
    }

}