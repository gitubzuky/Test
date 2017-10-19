
package com.lib.mylib.httptest;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/31.
 */

public class RequestUtil {
    private final int REQUEST_FLAG_GET = 1;
    private final int REQUEST_FLAG_POST = 2;

    private int requestFlag;

    RequestBuilder builder;
    boolean[] isRequesting = {};

    RequestDirector director;

    public RequestUtil() {
        director = new RequestDirector();
    }

    public void get(String baseUrl, String path, HashMap<String, String> params, int which,
            HttpCallback callback) {
        if (isRequesting[which]) {
            return;
        }
        isRequesting[which] = true;
        GetRequestBuilder builder = new GetRequestBuilder(new BaseParse());
        director.sendRequest(builder, baseUrl, path, params, which, callback);
        director.get(builder);
    }

    /**
     * 自定义的get请求
     * 调用方式：新建一个类继承RequestBuilder，重写其中方法，实现在自己的请求，作为RequestBuilder参数传入即可
     * 备注：RequestBuilder的构造函数的JsonParse参数也可以自己继承一个传入，或者用BaseParse传入
     * 
     * @param builder
     * @param baseUrl
     * @param path
     * @param params
     * @param which
     * @param callback
     */
    public void get(RequestBuilder builder, String baseUrl, String path,
            HashMap<String, String> params, int which,
            HttpCallback callback) {
        if (isRequesting[which]) {
            return;
        }
        isRequesting[which] = true;
        director.sendRequest(builder, baseUrl, path, params, which, callback);
        director.get(builder);
    }

    public void post(String baseUrl, String path, HashMap<String, String> params, int which,
            HttpCallback callback) {
        if (isRequesting[which]) {
            return;
        }
        isRequesting[which] = true;
        PostRequestBuilder builder = new PostRequestBuilder(new BaseParse());
        director.sendRequest(builder, baseUrl, path, params, which, callback);
        director.post(builder);
    }
}
