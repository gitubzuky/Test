
package com.example.administrator.test;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.test.view.IView;

public class ConstraintTestActivity extends AppCompatActivity implements IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_test);
    }

    @Override
    public void setTitle(String text) {

    }

    @Override
    public void setTitleTextColor(@ColorRes int color) {

    }

    @Override
    public void showToast(String toast, int duration) {

    }

    @Override
    public void changeText(String text) {

    }
}
