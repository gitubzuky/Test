
package com.example.administrator.test.presenter.meizi;

import android.widget.Toast;

import com.example.administrator.test.model.home.FunctionBean;
import com.example.administrator.test.view.home.IView;

/**
 * Created by Administrator on 2017/2/4.
 */

public class TestPresenter implements ILoginPresenter {
    private IView ivTest;

    private FunctionBean bean;

    public TestPresenter(IView ivTest) {
        this.ivTest = ivTest;

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
}
