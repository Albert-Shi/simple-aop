package com.playground.test;

import com.playground.test.core.InvaderInitialization;
import com.playground.test.core.ProxyFactory;
import com.playground.test.model.MyStar;

/**
 * @author shishuheng
 * @date 2020/1/10 5:56 下午
 */
public class Appilication {
    public static void main(String[] args) throws Exception {
        InvaderInitialization invaderInitialization = new InvaderInitialization("com.playground.test");
        ProxyFactory factory = new ProxyFactory(invaderInitialization);
        MyStar star = factory.create(MyStar.class);
        star.sing("刘德华");
        System.out.println("");
        star.dance("郭富城");
    }
}
