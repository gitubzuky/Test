
package com.example.administrator.test.presenter;

import android.widget.Toast;

import com.example.administrator.test.model.TestBean;
import com.example.administrator.test.view.IView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/4.
 */

public class TestPresenter implements ILoginPresenter {
    private IView ivTest;

    private Subscriber<String> subscriber;

    private TestBean bean;

    public TestPresenter(IView ivTest) {
        this.ivTest = ivTest;

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

    @Override
    public void login(String userName) {
        if ("1".equals(userName)) {
            ivTest.showToast("登录成功", Toast.LENGTH_SHORT);
            ivTest.changeText("已登录");
        } else {
            ivTest.showToast("登录失败", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 用来测试转换的类
     */
    private class FlatMapTest {
        private int i;
        private List<String> str;

        public FlatMapTest(int i, List<String> str) {
            this.i = i;
            this.str = str;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public List<String> getStr() {
            return str;
        }

        public void setStr(List<String> str) {
            this.str = str;
        }
    }

    /**
     * flatMap的测试
     */
    public void doRxjavaflatMapTask() {

        List<String> temp = new ArrayList<>();
        temp.add("零");
        FlatMapTest test = new FlatMapTest(0, temp);

        List<String> temp1 = new ArrayList<>();
        temp1.add("零");
        temp1.add("一");
        FlatMapTest test1 = new FlatMapTest(1, temp1);

        List<String> temp2 = new ArrayList<>();
        temp2.add("零");
        temp2.add("一");
        temp2.add("二");
        FlatMapTest test2 = new FlatMapTest(2, temp2);

        List<String> temp3 = new ArrayList<>();
        temp3.add("零");
        temp3.add("一");
        temp3.add("二");
        temp3.add("三");
        FlatMapTest test3 = new FlatMapTest(3, temp3);
        FlatMapTest[] tests = {
                test, test1, test2, test3
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                ivTest.showToast(s, Toast.LENGTH_SHORT);
            }
        };

        Observable.from(tests)
                .flatMap(new Func1<FlatMapTest, Observable<String>>() {
                    @Override
                    public Observable<String> call(FlatMapTest flatMapTest) {
                        return Observable.from(flatMapTest.getStr());
                    }
                })
                .subscribe(subscriber);

    }
}
