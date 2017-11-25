
package com.example.administrator.test.presenter.meizi;

import android.app.Activity;

/**
 * Created by Zuky on 2017/2/10.
 * <p>Description: 妹子页面逻辑处理presenter接口</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */

public interface IMeiziPresenter {

    void loadMeiziImgsTest(String type, int limit, int page);

    void nextMeizi();

    void loadMeiziByPosition(int position);// 根据position加载Meizi图片

    void loadDate();

    void loadNextGroupOfMeizi();

    void loadPreviousGroupOfMeizi();

    /**
     * 点击图片查看大图
     * 
     * @param activity 当前activity
     * @param locationX 点击的图片的x坐标
     * @param locationY 点击的图片的y坐标
     * @param width 点击的图片的宽
     * @param height 点击的图片的高
     */
    void showBigPic(Activity activity, int locationX, int locationY, int width, int height);
}
