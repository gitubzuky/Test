
package com.example.administrator.test.view.widgettest;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.example.administrator.test.view.IBaseView;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IWidgetTestView extends IBaseView {
    /**
     * vLayout添加布局（adapter）
     * 
     * @param adapter
     */
    void vLayoutAddAdapter(DelegateAdapter.Adapter adapter);

    /**
     * vLayout刷新
     */
    void vLayoutNotifyDataChange();

    /**
     * switch切换
     * 
     * @param state
     */
    void changeState(boolean state);
}
