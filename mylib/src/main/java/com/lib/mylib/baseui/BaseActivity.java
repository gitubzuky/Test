
package com.lib.mylib.baseui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.lib.mylib.R;

public abstract class BaseActivity extends AppCompatActivity {
    ViewGroup vgContentLayout;
    ViewGroup vgCurContent;
    LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);

        vgContentLayout = (ViewGroup) findViewById(R.id.base_data);

        initVariable();
        setTitleView();
        setViews();
    }

    /**
     * 设置页面内容
     * 
     * @param layoutRes
     */
    public void setContentViewRes(@LayoutRes int layoutRes) {
        vgContentLayout.removeAllViews();
        vgCurContent = (ViewGroup) mInflater.inflate(layoutRes, null);
        vgContentLayout.addView(vgCurContent, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    protected abstract void initVariable();

    public abstract void setTitleView();

    public abstract void setViews();
}
