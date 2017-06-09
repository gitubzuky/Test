
package com.example.administrator.test.presenter;

/**
 * Created by Administrator on 2017/2/10.
 */

public interface IMeiziPresenter {

    void loadMeiziImgsTest(String type, int limit, int page);

    void nextMeizi();

    void loadDate();

    void loadNextGroupOfMeizi();

    void loadPreviousGroupOfMeizi();
}
