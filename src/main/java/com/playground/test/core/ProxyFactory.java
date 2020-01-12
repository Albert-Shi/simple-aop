package com.playground.test.core;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shishuheng
 * @date 2020/1/10 4:48 下午
 */
public class ProxyFactory {
    private static final Map<Class, Object> cache = new LinkedHashMap<>();
    private static final Map<Class, Object> originalObjects = new LinkedHashMap<>();
    private InvaderInitialization initialization = null;

    public ProxyFactory(InvaderInitialization initialization) {
        this.initialization = initialization;
    }

    public <T> T create(Class<T> clazz) {
        Object obj = cache.get(clazz);
        if (null == obj) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            ProxyCallbackFilter filter = new ProxyCallbackFilter();
            enhancer.setCallbackFilter(filter);
            ProxyInterceptor interceptor = new ProxyInterceptor();
            enhancer.setCallback(interceptor);
            obj = enhancer.create();
            cache.put(clazz, obj);
        }
        Object original = originalObjects.get(clazz);
        if (null == original) {
            try {
                original = clazz.newInstance();
                originalObjects.put(clazz, original);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) obj;
    }

    private class ProxyInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Object original = originalObjects.get(method.getDeclaringClass());
            if (null == original) {
                return methodProxy.invokeSuper(o, objects);
            }
            ProcessTarget target = new ProcessTarget(original, method.getName(), objects);
            String key = method.getDeclaringClass().getName() + "." + method.getName() + "()";
            InvaderWrapper top = initialization.getTopInvaderMethods().get(key);
            InvaderWrapper bottom = initialization.getBottomInvaderMethods().get(key);
            InvaderWrapper sandwich = initialization.getSandwichInvaderMethods().get(key);
            Object result = null;
            if (null != top || null != bottom || null != sandwich) {
                if (null != top) {
                    top.setProcessTarget(target);
                    Object res = top.invokeTarget();
                    if (null != res) {
                        result = res;
                    }
                }
                if (null != sandwich) {
                    sandwich.setProcessTarget(target);
                    Object res = sandwich.invokeTarget();
                    if (null != res) {
                        result = res;
                    }
                }
                if (null != bottom) {
                    bottom.setProcessTarget(target);
                    Object res = bottom.invokeTarget();
                    if (null != res) {
                        result = res;
                    }
                }
            } else {
                result = method.invoke(original, objects);
            }
            return result;
        }
    }

    private class ProxyCallbackFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            String key = method.getClass().getName() + "." + method.getName() + "()";
            if (null != initialization.getTopInvaderMethods().get(key)
                    || null != initialization.getBottomInvaderMethods().get(key)
                    || null != initialization.getSandwichInvaderMethods().get(key)) {
                return 1;
            }
            return 0;
        }
    }
}
