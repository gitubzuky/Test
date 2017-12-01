package com.example.administrator.test.presenter.dagger2test;

import android.widget.Toast;

import com.example.administrator.test.view.IBaseView;
import com.example.administrator.test.view.daggertest.IDagger2TestView;

/**
 * <p>Description: dagger2test页面逻辑</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Created by Zuky on 2017/11/28.</p>
 */
public class Dagger2TestPresenter implements IDagger2TestPresenter{
    IDagger2TestView dagger2TestView;

    public Dagger2TestPresenter(IDagger2TestView dagger2TestView) {
        this.dagger2TestView = dagger2TestView;
    }

    @Override
    public void doOnBrew(String status) {
        switch (status){
            case "on":
                dagger2TestView.showToast("开始冲咖啡", Toast.LENGTH_SHORT);
                dagger2TestView.changeCoffeeMakerStatus("加热中");
                break;
            case "pump":
                dagger2TestView.showToast("开始泵水", Toast.LENGTH_SHORT);
                dagger2TestView.changeCoffeeMakerStatus("泵水中");
                break;
            case "off":
                dagger2TestView.showToast("关机", Toast.LENGTH_SHORT);
                dagger2TestView.changeCoffeeMakerStatus("完成");
                break;
        }

    }
}
