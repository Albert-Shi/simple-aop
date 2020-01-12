package com.playground.test.invaders;

import com.playground.test.annotation.Bottom;
import com.playground.test.annotation.Invader;
import com.playground.test.annotation.Sandwich;
import com.playground.test.annotation.Top;
import com.playground.test.core.ProcessTarget;

/**
 * @author shishuheng
 * @date 2020/1/10 1:52 下午
 */
@Invader
public class FirstInvader {
    @Sandwich("com.playground.test.model.MyStar.sing()")
    public Object invaderSandwich(ProcessTarget target) {
        try {
            System.out.println("==== 开始 ====");
            System.out.println("方法名：" + target.getMethodName());
            Object res = target.process();
            System.out.println("返回体：" + res);
            System.out.println("==== 结束 ====");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
            return null;
        }
    }

    @Top("com.playground.test.model.MyStar.dance()")
    public void invaderTop() {
        try {
            System.out.println("=== 这是一个在执行之前执行的方法");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
        }
    }

    @Bottom("com.playground.test.model.MyStar.dance()")
    public void invaderBottom() {
        try {
            System.out.println("=== 这是一个在执行完成后执行的方法");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
//            return null;
        }
    }
}
