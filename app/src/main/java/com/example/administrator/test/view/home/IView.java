
package com.example.administrator.test.view.home;

import android.support.annotation.ColorRes;

/**
 * <p>Description: 首页View接口</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/24.</p>
 */
public interface IView {
    void setTitle(String text);

    void setTitleTextColor(@ColorRes int color);

    void showToast(String toast, int duration);

    void changeText(String text);
}
