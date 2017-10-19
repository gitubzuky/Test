
package com.lib.mylib.httptest;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/31.
 */

public interface RequestBuilder {
    void baseUrl(String baseUrl);

    void path(String path);

    void params(HashMap<String, String> params);

    void callback(HttpCallback callback);

    void which(int which);

    void cashMode();

    void get();

    void post();
}
