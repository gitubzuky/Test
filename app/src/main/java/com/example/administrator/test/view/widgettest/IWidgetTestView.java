
package com.example.administrator.test.view.widgettest;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.example.administrator.test.view.IBaseView;

/**
 * Created by Zuky on 2017/10/25.
 * <p>Description: 控件测试页面View接口</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
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
