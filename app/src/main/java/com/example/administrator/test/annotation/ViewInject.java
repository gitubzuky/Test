package com.example.administrator.test.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Zuky on 2016/10/15.
 * <p>Description: 自定义注解测试</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */
@Target(ElementType.FIELD)//表示用在字段上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
public @interface ViewInject {
    @IdRes int value() default 0;
}
