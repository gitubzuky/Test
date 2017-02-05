
package com.example.administrator.test;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.annotation.Injection;
import com.example.administrator.test.annotation.ViewInject;
import com.example.administrator.test.presenter.TestPresenter;
import com.example.administrator.test.view.IView;

/**
 * Created by Administrator on 2016/10/14.
 */

public class TestActivity extends AppCompatActivity implements IView {
    @ViewInject(R.id.tv_test_title)
    TextView tvTitle;
    @ViewInject(R.id.act_test_btn_rxjava_test)
    Button btnRxjavaTest;

    TestPresenter testPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Injection.inject(this);
        tvTitle.setText("测试");

        initPresenter();
        initView();
    }

    private void initPresenter() {
        testPresenter = new TestPresenter(this);
    }

    private void initView() {
        btnRxjavaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // testPresenter.doTask();
                testPresenter.doRxjavaflatMapTask();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setTitle(String text) {

    }

    @Override
    public void setTitleTextColor(@ColorRes int color) {

    }

    @Override
    public void showToast(String toast, int duration) {
        switch (duration) {
            case Toast.LENGTH_SHORT:
                Toast.makeText(TestActivity.this, toast, Toast.LENGTH_SHORT).show();
                break;
            case Toast.LENGTH_LONG:
                Toast.makeText(TestActivity.this, toast, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
