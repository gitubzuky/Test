
package com.example.administrator.test.ui.daggertest;

/**
 * <p>
 * Description: 加热器抽象类
 * </p>
 * <p>
 * Tips:
 * </p>
 * <p>
 * Version: 1.0
 * </p>
 * <p>
 * Created by Zuky on 2017/11/27.
 * </p>
 */
public interface Heater {
    void on();

    void off();

    boolean isHot();
}
