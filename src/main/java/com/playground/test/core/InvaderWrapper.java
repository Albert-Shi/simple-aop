package com.playground.test.core;

import com.playground.test.annotation.Bottom;
import com.playground.test.annotation.Sandwich;
import com.playground.test.annotation.Top;

import java.lang.reflect.Method;

/**
 * @author shishuheng
 * @date 2020/1/10 2:44 下午
 */
public class InvaderWrapper {
    private Object invader;
    private Method method;
    private String targetMethodName;
    private ProcessTarget processTarget;

    public InvaderWrapper(Object invader, Method invaderMethod, String targetMethodName) {
        this.invader = invader;
        this.method = invaderMethod;
        this.targetMethodName = targetMethodName;
    }

    public Object invokeTarget() throws Exception {
        if (null == method) {
            return null;
        }
        Class[] types = method.getParameterTypes();
        if (method.getAnnotation(Top.class) != null) {
            if (null != types && types.length > 0) {
                return method.invoke(invader, processTarget);
            } else {
                method.invoke(invader);
                return processTarget.process();
            }
        } else if (method.getAnnotation(Sandwich.class) != null) {
            if (null != types && types.length > 0) {
                return method.invoke(invader, processTarget);
            } else {
                return method.invoke(invader);
            }
        } else if (method.getAnnotation(Bottom.class) != null) {
            if (null != types && types.length > 0) {
                return method.invoke(invader, processTarget);
            } else {
                Object res = processTarget.process();
                method.invoke(invader);
                return res;
            }
        } else {
            return processTarget.process();
        }
    }

    public void setProcessTarget(ProcessTarget processTarget) {
        this.processTarget = processTarget;
    }
}
