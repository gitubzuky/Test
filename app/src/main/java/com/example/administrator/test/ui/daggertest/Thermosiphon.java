package com.example.administrator.test.ui.daggertest;

import javax.inject.Inject;

/**
 * <p>Description: 热虹吸管水泵</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Created by Zuky on 2017/11/27.</p>
 */
public class Thermosiphon implements Pump {
    private final Heater heater;
    @Inject
    public Thermosiphon(Heater heater) {
        this.heater = heater;
    }

    @Override
    public void pump() {
        if (heater.isHot()){
            // TODO: 待定，这里逻辑处理有点不懂

        }
    }
}
