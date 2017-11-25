
package com.example.administrator.test.presenter.home;

import android.view.View;

import com.example.administrator.test.model.home.FunctionBean;

import java.util.List;

/**
 * Created by Zuky on 2016/9/13.
 * <p>Description: 首页页面逻辑presenter接口</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
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
