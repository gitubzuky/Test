package com.example.administrator.test.ui.daggertest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Description: 滴滤咖啡Module</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Created by Zuky on 2017/11/27.</p>
 */
@Module(includes = PumpModule.class)
public class DripCoffeeModule {
    @Provides
    @Singleton
    Heater provideHeater(){
        return new ElectricHeater();
    }
}
