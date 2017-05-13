
package com.example.administrator.test.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.ConstraintTestActivity;
import com.example.administrator.test.R;
import com.example.administrator.test.TestActivity;
import com.example.administrator.test.VideoActivity;
import com.example.administrator.test.model.TestBean;
import com.example.administrator.test.view.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面presenter Created by Administrator on 2016/9/13.
 */
public class MainPresenter implements IPresenter {
    private List<TestBean> dataList;
    private IView vMain;
    private String[] info = {
            "测试1", "测试2", "测试3"
    };

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
                // ImageView ivItem = (ImageView)
                // view.findViewById(R.id.iv_item);
                // setImage(ivItem);
                vMain.setTitleTextColor(R.color.colorPrimary);
                vMain.showToast(dataList.get(position).getText(), Toast.LENGTH_SHORT);
                startConstraintActicity();
                break;
            case 1:
                // showListToast();// 测试一下git分支
                // startTestActivity();
                // vMain.setTitleTextColor(R.color.colorAccent);
                // vMain.showToast(dataList.get(position).getText(),
                // Toast.LENGTH_SHORT);
                startActivity(VideoActivity.class);
                break;
            default:
                break;
        }

    }

    private void startTestActivity() {
        Intent i = new Intent((Activity) vMain, TestActivity.class);
        ((Activity) vMain).startActivity(i);
    }

    private void startActivity(Class clzz) {
        Intent i = new Intent((Activity) vMain, clzz);
        ((Activity) vMain).startActivity(i);
    }

    private void startConstraintActicity() {
        Intent i = new Intent((Activity) vMain, ConstraintTestActivity.class);
        ((Activity) vMain).startActivity(i);
    }
}
