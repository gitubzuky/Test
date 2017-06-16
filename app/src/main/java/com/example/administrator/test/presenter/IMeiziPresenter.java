
package com.example.administrator.test.presenter;

import android.app.Activity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

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

    void showBigPic(Activity activity, int locationX, int locationY, int width, int height,
            DisplayImageOptions options);
}
