package com.kowksiuwang.everythingaboutandroid.kotlin;

import org.junit.Test;

/**
 * string类，涉及到三个存储区域，栈，堆，字符串池。
 * 栈，用来存放变量，内容是一个指向堆的指针。
 * 堆，用来存放String对象，内容是一个指向字符串池的指针（字符串真正的值）+很多String类的方法。
 * 字符串池，存放字符串，字符串不会重复。字符串的唯一性是由字符串池去保证的（不是堆）。
 * <p>
 * 对于用String s = new String("text")创建出来的对象，每次用==去对比都是不一样的，因为它们栈(堆的引用)和堆的值都不一样，只有字符串池的值是相同的。
 * intern()可以返回字符串的规范化表示形式。任何两个string，当且仅当equals()==true时，intern()==intern()为true。
 * <p>
 * 对于使用常量定义的String s = "abc"，系统就会帮我们去看下堆中是否有相同的值，如果有，会把引用指向同一个堆中的对象。
 * 这就保证了，String s1 = "abc";String s2 = "abc";s1==s2为true，因为堆的地址相同。
 * <p>
 * 对于用+号来做字符串拼接。
 * 假设表达式中只有常量，系统会默认把常量都合并起来，并且在堆中寻找相同的对象。
 * 比如，String a = "java",b = "ja" + "va";a == b;//true
 * 假设中间出现了String对象，那系统会创建新的堆对象。
 * 即使是这样，String a = "java",b = a + ""; a == b;//false
 * <p>
 * <p>
 * Created by kwoksiuwang on 12/11/20!!!
 */
public class StringTestJava {
    @Test
    public void Test() {
        String a = "ja";
        String b = "va";
        String c = a + b;
        String d = "java";
        String e = "java";
        String f = "ja" + "va" + "";
        String g = "";
        String h = d + g;
        String i = a + "";
        String j = a + "va";

        //false
        System.out.println(c == d);

        //true
        System.out.println(d == e);

        //true
        System.out.println(d == f);

        //true
        System.out.println(d == "ja" + "va");

        //false
        System.out.println(d == h);

        //false
        System.out.println(d == i);

        //false
        System.out.println(d == j);

        //下面都是true
//        System.out.println("ab".intern() == "ab".intern());
//        System.out.println("ab".intern() == (new String("ab")).intern());
//        System.out.println((new String("ab")).intern() == (new String("ab")).intern());
//        System.out.println(d.intern() == j.intern());
//        System.out.println(d.intern() == (new String("java")).intern());
        System.out.println(j.intern() == (new String("java")).intern());
//        System.out.println(j.equals(d));


        d.intern();
    }
}
