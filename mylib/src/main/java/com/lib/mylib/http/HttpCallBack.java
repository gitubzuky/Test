
package com.lib.mylib.http;

/**
 * 网络请求回调<br/>
 * Created by qiujj on 2017/3/10.
 */
public interface HttpCallBack {
    void onResult(String baseJson, int which);

    void onError(int which);
}
