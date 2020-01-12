package com.playground.test.model;

/**
 * @author shishuheng
 * @date 2020/1/10 1:57 下午
 */
public class MyStar implements Star {
    @Override
    public String sing(String name) {
        System.out.println("唱歌🎤");
        return name + "唱歌🎤";
    }

    @Override
    public String dance(String name) {
        System.out.println("跳舞💃");
        return name + "跳舞💃";
    }
}
