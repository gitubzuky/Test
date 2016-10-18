package com.example.administrator.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.test.annotation.Injection;
import com.example.administrator.test.annotation.ViewInject;

/**
 * Created by Administrator on 2016/10/14.
 */

public class TestActivity extends Activity {
    @ViewInject(R.id.rv_test)
    RecyclerView rvTest;
    @ViewInject(R.id.tv_test_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Injection.inject(this);
        tvTitle.setText("测试");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
