package com.kowksiuwang.everythingaboutandroid.designmode;

public class SingleInstance {

}
//懒汉模式
class SIHungry{
    private static SIHungry instance;
    public static SIHungry getInstance(){
        if (instance!=null){
            synchronized (SIHungry.class){
                if (instance!=null){
                    instance = new SIHungry();
                }
            }
        }
        return instance;
    }
}
