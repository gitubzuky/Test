
package com.example.administrator.test.presenter;

import android.view.View;

import com.example.administrator.test.model.FunctionBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public interface IPresenter {
    List<FunctionBean> getData();

    /**
     * 处理list的item点击事件
     * 
     * @param view
     * @param position
     * @param l
     * @param viewType
     */
    void doItemClick(View view, int position, long l, int viewType);
}
