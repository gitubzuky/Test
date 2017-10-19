
package com.lib.mylib.baseui;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.lib.mylib.basepresenter.BasePresenter;

/**
 * Created by Administrator on 2017/3/4.
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity
        implements BaseView {
    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState, persistentState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            // mPresenter.detachView();
        }
    }
}
