
package com.example.administrator.test.ui.daggertest;

/**
 * <p>
 * Description: 电子加热器
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
public class ElectricHeater implements Heater {
    boolean isHeating;

    @Override
    public void on() {
        this.isHeating = true;
    }

    @Override
    public void off() {
        this.isHeating = false;
    }

    @Override
    public boolean isHot() {
        return isHeating;
    }
}
