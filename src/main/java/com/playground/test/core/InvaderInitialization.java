package com.playground.test.core;

import com.playground.test.annotation.Bottom;
import com.playground.test.annotation.Invader;
import com.playground.test.annotation.Sandwich;
import com.playground.test.annotation.Top;
import com.playground.test.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shishuheng
 * @date 2020/1/10 2:00 下午
 */
public class InvaderInitialization {
    private Map<String, InvaderWrapper> topInvaderMethods = new LinkedHashMap<>();
    private Map<String, InvaderWrapper> bottomInvaderMethods = new LinkedHashMap<>();
    private Map<String, InvaderWrapper> sandwichInvaderMethods = new LinkedHashMap<>();


    public InvaderInitialization(String packagePath) throws Exception {
        Set<Class<?>> classes = ClassUtils.getClasses(packagePath);
        List<Class<?>> invaders = classes.stream().filter(e -> null != e.getAnnotation(Invader.class)).collect(Collectors.toList());
        for (Class c : invaders) {
            Object obj = c.newInstance();
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                Top top = m.getAnnotation(Top.class);
                Bottom bottom = m.getAnnotation(Bottom.class);
                Sandwich sandwich = m.getAnnotation(Sandwich.class);
                if (null != top) {
                    InvaderWrapper wrapper = new InvaderWrapper(obj, m, methodName(top.value()));
                    topInvaderMethods.put(top.value(), wrapper);
                }
                if (null != bottom) {
                    InvaderWrapper wrapper = new InvaderWrapper(obj, m, methodName(bottom.value()));
                    bottomInvaderMethods.put(bottom.value(), wrapper);
                }
                if (null != sandwich) {
                    InvaderWrapper wrapper = new InvaderWrapper(obj, m, methodName(sandwich.value()));
                    sandwichInvaderMethods.put(sandwich.value(), wrapper);
                }
            }
        }
    }

    /**
     * 获取包名
     *
     * @param value
     * @return
     */
    private String parsePackageFromAnnotation(String value) {
        if (null == value || "".equals(value)) {
            return null;
        }
        if (!value.endsWith(")")) {
            return null;
        }
        int end = value.lastIndexOf(".");
        if (-1 == end) {
            return null;
        }
        String pack = value.substring(0, end);
        return pack;
    }

    private String methodName(String value) {
        if (null == value || "".equals(value)) {
            return null;
        }
        int start = value.lastIndexOf(".");
        if (-1 == start) {
            return null;
        }
        int end = value.lastIndexOf("(");
        if (-1 == end) {
            end = value.length();
        }
        return value.substring(start + 1, end);
    }

    public Map<String, InvaderWrapper> getTopInvaderMethods() {
        return topInvaderMethods;
    }

    public Map<String, InvaderWrapper> getBottomInvaderMethods() {
        return bottomInvaderMethods;
    }

    public Map<String, InvaderWrapper> getSandwichInvaderMethods() {
        return sandwichInvaderMethods;
    }
}
