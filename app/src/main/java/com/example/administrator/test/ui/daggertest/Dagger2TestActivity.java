
package com.example.administrator.test.ui.daggertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.test.R;
import com.example.administrator.test.view.daggertest.IDagger2TestView;

/**
 * <p>Description: Dagger2测试页面</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Created by Zuky on 2017/11/24.</p>
 */
public class Dagger2TestActivity extends AppCompatActivity implements IDagger2TestView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2_test);
    }

    @Override
    public void showToast(String toast, int duration) {

    }
}
