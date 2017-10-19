
package com.lib.mylib.httptest;

/**
 * Created by Administrator on 2017/9/1.
 */

public interface HttpCallback {
    void onResult(String dataJson, int which);

    void onError(int which);
}
