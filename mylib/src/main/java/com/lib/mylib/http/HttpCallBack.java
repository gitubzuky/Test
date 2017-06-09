
package com.lib.mylib.http;

import java.util.List;

/**
 * 网络请求回调 - 只返回数据<br/>
 * Created by qiujj on 2017/3/10.
 */
public interface HttpCallBack<T> {
    int ERROR_CODE_400 = 400;// 数据出错
    int ERROR_CODE_500 = 500;// 网络错误

    void onResult(List<T> dataList, int which);

    boolean filter(List<T> dataBean);

    void onError(int errorCode, int which);
}
