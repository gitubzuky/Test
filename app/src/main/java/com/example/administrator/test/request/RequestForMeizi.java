
package com.example.administrator.test.request;

import com.example.administrator.test.model.MeiziTestBean;
import com.lib.mylib.http.HttpRequestService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 妹子Retrofit的请求接口 Created by Administrator on 2017/2/10.
 */
public interface RequestForMeizi extends HttpRequestService {
    @GET("{type}/{limit}/{page}")
    Call<MeiziTestBean> listMeizis(@Path("type") String type, @Path("limit") String limit,
            @Path("page") String page);

    @GET("{type}/{limit}/{page}")
    Call<String> getBaseJson(@Path("type") String type, @Path("limit") String limit,
            @Path("page") String page);
}
