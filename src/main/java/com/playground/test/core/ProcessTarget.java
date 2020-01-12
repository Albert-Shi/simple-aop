package com.playground.test.core;

import java.lang.reflect.Method;

/**
 * @author shishuheng
 * @date 2020/1/10 11:47 上午
 */
public class ProcessTarget {
    private Object target;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] params;
    private boolean processed;

    public ProcessTarget(Object target, String methodName, Object[] params) {
        this.target = target;
        this.methodName = methodName;
        this.paramTypes = getTypesFromObjects(params);
        this.params = params;
        this.processed = false;
    }

    public Object process() throws Exception {
        if (processed) {
            return null;
        }
        Method method = target.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        Object res = method.invoke(target, params);
        method.setAccessible(false);
        processed = true;
        return res;
    }

    private Class[] getTypesFromObjects(Object[] objects) {
        Class[] types = {};
        if (null != objects && objects.length > 0) {
            types = new Class[objects.length];
            for (int i = 0; i < objects.length; i++) {
                types[i] = objects[i].getClass();
            }
        }
        return types;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public boolean isProcessed() {
        return processed;
    }
}
