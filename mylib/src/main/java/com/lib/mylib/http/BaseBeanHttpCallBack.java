
package com.lib.mylib.http;

/**
 * 网络请求回调 - 基础bean<br/>
 * Created by qiujj on 2017/3/10.
 */
public interface BaseBeanHttpCallBack<T> {
    void onResult(T baseBean, int which);

    boolean filter(T baseBean);

    void onError(int which);
}
