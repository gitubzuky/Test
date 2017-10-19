
package com.lib.mylib.baseui;

import com.lib.mylib.basepresenter.BasePresenter;

/**
 * Created by Administrator on 2017/3/4.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
