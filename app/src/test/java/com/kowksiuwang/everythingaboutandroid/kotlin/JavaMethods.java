package com.kowksiuwang.everythingaboutandroid.kotlin;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/**
 * Created by kwoksiuwang on 12/10/20!!!
 */
public class JavaMethods {
    void test() {
        MyFunction<String, Integer> stringLength = new MyFunction<String, Integer>() {
            @Override
            public Integer apple(String s) {
                return s.length();

            }
        };
        stringLength.apple("34");

        MyFunction<String, Void> print = new MyFunction<String, Void>() {
            @Override
            public Void apple(String s) {
                return null;
            }
        };

        HighClassMethod hcm = new HighClassMethod();
        hcm.m1(new Function1<Integer, Unit>() {
            @Override
            public Unit invoke(Integer integer) {
                return null;
            }
        });
        hcm.m3(new Function3<Integer, Integer, String, Boolean>() {
            @Override
            public Boolean invoke(Integer integer, Integer integer2, String s) {
                return false;
            }
        });
    }

    void n1(Function1<Integer, String> function) {

    }

    void n2(Function1<Integer, String> f1, Function2<String, String, Integer> f2) {
        String result = f1.invoke(1);
        f2.invoke(result, "ok");
    }

}

interface MyFunction<Arg, Return> {
    Return apple(Arg arg);
}