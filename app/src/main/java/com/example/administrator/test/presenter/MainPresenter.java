package com.example.administrator.test.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.example.administrator.test.TestActivity;
import com.example.administrator.test.adapter.MainAdapter;
import com.example.administrator.test.model.TestBean;
import com.example.administrator.test.view.IView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 主界面presenter
 * Created by Administrator on 2016/9/13.
 */
public class MainPresenter implements IPresenter {
    private List<TestBean> dataList;
    private IView vMain;
    private String[] info = {"测试1", "测试2", "测试3"};

    public MainPresenter(IView vMain) {
        this.vMain = vMain;
    }

    @Override
    public List<TestBean> getData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            TestBean testBean = new TestBean();
            if (i % 3 == 0) {
                testBean.setText("类型1");
            } else {
                testBean.setText("类型2");
            }
            dataList.add(testBean);
        }

        return dataList;
    }

    @Override
    public void doItemClick(final View view, int position, long l, int viewType) {
        switch (viewType) {
            case 0:
//                ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
//                setImage(ivItem);



                vMain.setTitleTextColor(R.color.colorPrimary);
                vMain.showToast(dataList.get(position).getText(), Toast.LENGTH_SHORT);
                break;
            case 1:
//                showListToast();

                startTestActivity();
                vMain.setTitleTextColor(R.color.colorAccent);
                vMain.showToast(dataList.get(position).getText(), Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }

    }

    private void startTestActivity() {
        Intent i = new Intent((Activity) vMain, TestActivity.class);
        ((Activity) vMain).startActivity(i);
    }

    private void showListToast() {
        Observable.from(info)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        vMain.showToast(s, Toast.LENGTH_SHORT);
                    }
                });
    }

    private void setImage(final ImageView ivItem) {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = ((Activity) vMain).getResources().getDrawable(R.drawable.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(((Application) vMain), "图片获取错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                ivItem.setImageDrawable(drawable);
            }
        });
    }
}
