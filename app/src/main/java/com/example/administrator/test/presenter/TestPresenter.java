
package com.example.administrator.test.presenter;

import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.model.TestBean;
import com.example.administrator.test.view.IView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/4.
 */

public class TestPresenter implements IPresenter {
    private IView ivTest;

    private Subscriber<String> subscriber;

    public TestPresenter(IView ivTest) {
        this.ivTest = ivTest;

    }

    @Override
    public List<TestBean> getData() {
        return null;
    }

    @Override
    public void doItemClick(View view, int position, long l, int viewType) {

    }

    /**
     * 简单的Rxjava点击测试
     */
    public void doTask() {
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                ivTest.showToast("onCompleted", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                ivTest.showToast(s, Toast.LENGTH_SHORT);
            }
        };

        Observable observable = Observable.just("onNext", "onNext2");

        observable.subscribe(subscriber);
    }
}
