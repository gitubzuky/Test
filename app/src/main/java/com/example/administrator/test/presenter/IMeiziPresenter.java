
package com.example.administrator.test.presenter;

import android.app.Activity;

/**
 * Created by Administrator on 2017/2/10.
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
