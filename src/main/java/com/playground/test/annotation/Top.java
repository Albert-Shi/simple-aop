package com.playground.test.annotation;

import java.lang.annotation.*;

/**
 * @author shishuheng
 * @date 2020/1/10 1:44 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Top {
    String value() default "";
}
