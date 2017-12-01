package com.example.administrator.test.ui.daggertest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * <p>Description: Component类</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Created by Zuky on 2017/11/24.</p>
 */
@Singleton
@Component(modules = {DripCoffeeModule.class})
public interface CoffeeShop {
    CoffeeMaker maker();
}
