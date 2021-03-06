
package com.lib.mylib.http;

import com.lib.mylib.bean.BaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by qiujj on 2017/3/10.
 */

public interface HttpRequestService<T> {
    @GET
    Call<String> get4GetBaseJson(@Url String path);

    @GET
    Call<String> get4GetBaseJson(@Url String path, @QueryMap Map<String, Object> params);

    @POST
    Call<String> post4GetBaseJson(@Url String path, @QueryMap Map<String, Object> params);

    @GET
    Observable<String> get4GetBaseBean(@Url String path);

    @POST
    Observable<String> post4GetBaseBean(@Url String path);

    @GET
    Observable<BaseBean> get4GetModelBean(@Url String path);

    @POST
    Observable<List<T>> post4GetModelBean(@Url String path);
}
