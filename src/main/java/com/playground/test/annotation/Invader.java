package com.playground.test.annotation;

import java.lang.annotation.*;

/**
 * @author shishuheng
 * @date 2020/1/10 1:35 下午
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invader {
}
