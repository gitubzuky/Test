package com.example.administrator.test.annotation;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * Created by Zuky on 2016/10/15.
 * <p>Description: 自定义注解测试</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */

public class Injection {
    public static void inject(Activity act){
        try {
            Class<?> clazz = act.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if(field.isAnnotationPresent(ViewInject.class)){
                    ViewInject inject = field.getAnnotation(ViewInject.class);
                    int id = inject.value();
                    if(id > 0){
                        field.setAccessible(true);// 设置可以从外部读取private字段的属性值
                        field.set(act, act.findViewById(id));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
