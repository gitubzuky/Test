
package com.example.administrator.test.ui.home;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.example.administrator.test.adapter.BaseRvAdapter;
import com.example.administrator.test.adapter.home.MainAdapter;
import com.example.administrator.test.presenter.home.IPresenter;
import com.example.administrator.test.presenter.home.MainPresenter;
import com.example.administrator.test.view.home.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>
 * Description: 首页
 * </p>
 * <p>
 * Tips:
 * </p>
 * <p>
 * Version: 1.0
 * </p>
 * <p>
 * Update by Zuky on 2017/11/24.
 * </p>
 */
public class MainActivity extends AppCompatActivity
        implements IView, BaseRvAdapter.OnRvItemClickListener {
    @BindView(R.id.act_main_tv_title)
    TextView tvTitle;
    @BindView(R.id.act_main_rv_function)
    RecyclerView rvFunction;
    private IPresenter presenter;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    private void initView() {
        // lvMain = (ListView) findViewById(R.id.lv_main);
        // mAdapter = new MainAdapter(this, presenter.getData());
        // lvMain.setAdapter(mAdapter);
        // lvMain.setOnItemClickListener(this);
        mAdapter = new MainAdapter(this, presenter.getData());
        rvFunction.setLayoutManager(new LinearLayoutManager(this));
        rvFunction.setAdapter(mAdapter);
        mAdapter.setRvItemClickListener(this);
    }

    private void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    public void setTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvTitle.setText(text);
        }
    }

    @Override
    public void setTitleTextColor(@ColorRes int color) {
        tvTitle.setTextColor(getResources().getColor(color));
    }

    @Override
    public void showToast(String toast, int duration) {
        switch (duration) {
            case Toast.LENGTH_SHORT:
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
                break;
            case Toast.LENGTH_LONG:
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void changeText(String text) {

    }

    @Override
    public void OnRvItemClick(View view, int position) {
        presenter.doItemClick(view, position);
    }
}
