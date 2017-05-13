
package com.lib.mylib.basepresenter;

import com.lib.mylib.baseui.BaseView;

/**
 * Created by Administrator on 2017/3/4.
 */

public abstract class BasePresenter<T extends BaseView> {
    private T mBaseView;

    public void attachView(T _baseView) {
        this.mBaseView = _baseView;
    }

    public void detachView() {
        mBaseView = null;
    }
}
