
package com.lib.mylib.httptest;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/2.
 */

public class RequestDirector {
    public void sendRequest(RequestBuilder builder, String baseUrl, String path,
            HashMap<String, String> params,
            int which, HttpCallback callback) {
        if (builder == null) {
            return;
        }
        if (!TextUtils.isEmpty(baseUrl)) {
            builder.baseUrl(baseUrl);
        }
        if (!TextUtils.isEmpty(path)) {
            builder.path(path);
        }
        if (params != null) {
            builder.params(params);
        }
        if (callback != null) {
            builder.callback(callback);
        }
    }

    public void get(RequestBuilder builder) {
        if (builder != null) {
            builder.get();
        }
    }

    public void post(RequestBuilder builder) {
        if (builder != null) {
            builder.post();
        }
    }
}
