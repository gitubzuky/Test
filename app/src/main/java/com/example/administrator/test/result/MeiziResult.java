
package com.example.administrator.test.result;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.test.model.meizi.MeiziTestBean;
import com.example.administrator.test.request.RequestForMeizi;
import com.lib.mylib.http.BaseConverterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MeiziResult {
    String baseUrl = "http://gank.io/api/data/";

    private HttpCallback mHttpCallback;

    private List<MeiziTestBean.ResultsBean> meiziBeanList = new ArrayList<>();

    public String getData(@NonNull String type, @NonNull String limit, @NonNull String page,
            final HttpCallback mHttpCallback) {

        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(limit) || TextUtils.isEmpty(page)) {
            return "400";
        }
        if (mHttpCallback != null) {
            this.mHttpCallback = mHttpCallback;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(BaseConverterFactory.create())
                .build();
        final RequestForMeizi requestForMeizi = retrofit.create(RequestForMeizi.class);
        // Call<MeiziTestBean> call = requestForMeizi.listMeizis(type, limit,
        // page);
        // call.enqueue(new Callback<MeiziTestBean>() {
        // @Override
        // public void onResponse(Call<MeiziTestBean> call,
        // Response<MeiziTestBean> response) {
        // Log.i("Retrofit", "onResponse: " + response.body());
        // meiziBeanList = response.body().getResults();
        // if (mBaseHttpCallback != null) {
        // mBaseHttpCallback.onResult(response.body().getResults());
        // }
        // }
        //
        // @Override
        // public void onFailure(Call<MeiziTestBean> call, Throwable t) {
        // if (mBaseHttpCallback != null) {
        // mBaseHttpCallback.onError();
        // }
        // }
        // });
        Call<String> call = requestForMeizi.getBaseJson(type, limit, page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Log.d("http", "onResponse:" + response.body());
                if (mHttpCallback != null) {
                    mHttpCallback.onResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("http", "请求出错" + t.toString());
                if (mHttpCallback != null) {
                    mHttpCallback.onError();
                }
            }
        });
        return "200";
    }

    public interface HttpCallback {
        /**
         * 直接使用GsonFactory来解析
         * 
         * @param meiziBeanList
         */
        void onResult(List<MeiziTestBean.ResultsBean> meiziBeanList);

        /**
         * 截取原始json进行处理
         * 
         * @param baseJson
         */
        void onResult(String baseJson);

        /**
         * 出错回调
         */
        void onError();
    }
}
