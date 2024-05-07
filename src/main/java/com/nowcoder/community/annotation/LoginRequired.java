package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 对方法生效
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface LoginRequired {
    // 什么都不用写，自定义注解，这里只起到一个标记和配置初属性的作用
}

