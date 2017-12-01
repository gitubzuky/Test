
package com.example.administrator.test.ui.daggertest;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 * Description: 咖啡机
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
public class CoffeeMaker {
    private final Lazy<Heater> heater; // Create a possibly costly heater only when we use it.
    private final Pump pump;
    private CompositeDisposable disposables;
    private int count = 0;
    private ObservableSource<String> openObservable, pumpObservable, offObservable;
    Observable<String> makerObservable;

    @Inject
    public CoffeeMaker(Lazy<Heater> heater, Pump pump) {
        this.heater = heater;
        this.pump = pump;
        disposables = new CompositeDisposable();
    }

    /**
     * 煮咖啡
     * @param openObserver
     */
    public void brew(DisposableObserver openObserver) {
        heater.get().on();
        makerObservable = sampleObservable();
        makerObservable.concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                if ("on".equals(s)){
                    return openObservable;
                } else if ("pump".equals(s)){
                    return pumpObservable;
                } else {
                    return offObservable;
                }
            }
        }).subscribeWith(openObserver);
    }

    /**
     * 获取三个步骤合并后的Observable
     * TODO:但是在订阅后事件触发时间仍然有点问题
     * @return
     */
    Observable<String> sampleObservable() {
        openObservable = Observable.just("on").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        pumpObservable = Observable.timer(3, TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return "pump";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        offObservable = Observable.timer(3, TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return "off";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return Observable.concat(openObservable, pumpObservable, offObservable);
    }

    public void pumpCoffee() {
        pump.pump();
    }

    public void offMaker() {
        heater.get().off();
        if (makerObservable != null){

        }
    }
}
