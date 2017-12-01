package com.example.administrator.test.ui.daggertest;

import dagger.Binds;
import dagger.Module;

/**
 * <p>Description: Pump Dagger的Module</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Created by Zuky on 2017/11/27.</p>
 */
@Module
public abstract class PumpModule {
    @Binds
    abstract Pump providePump(Thermosiphon pump);
}
