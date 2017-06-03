
package com.lib.mylib.http;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.Url;

/**
 * 封装的Retrofit网络请求<br/>
 * 请求发起方法：BaseRequest.getInstance() <br/>
 * .baseUrl(String baseUrl) <br/>
 * .path(String path)<br/>
 * .params(String key, String value)(可选)<br/>
 * .callBack(new BaseHttpCallBack())<br/>
 * .get();<br/>
 * 或<br/>
 * BaseRequest.getInstance() <br/>
 * .baseUrl(String baseUrl) <br/>
 * .path(String path)<br/>
 * .params(String key, String value)(可选)<br/>
 * .callBack(new BaseHttpCallBack())<br/>
 * .post();<br/>
 * TODO:未完成的优化或者问题有:<br/>
 * 1.多个请求并行的处理 <br/>
 * Created by qiujj on 2017/3/9.
 */
public class BaseRequest {
    private static final String TAG = "mylib";
    public int which = 0;
    public boolean[] isRequesting = {
            false, false, false, false, false, false, false, false, false, false
    };

    public String baseUrl = "";
    public String path = "";
    private static Map<String, Object> params;
    private BaseHttpCallBack mBaseHttpCallback;
    private static BaseRequest mBaseRequest = new BaseRequest();

    public BaseRequest() {

    }

    /**
     * 饿汉单例
     *
     * @return
     */
    public static BaseRequest getInstance() {
        params = new HashMap<String, Object>();
        return mBaseRequest;
    }

    public BaseRequest baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return mBaseRequest;
    }

    public BaseRequest path(@Url String path) {
        this.path = path;
        return mBaseRequest;
    }

    public BaseRequest param(String key, Object value) {
        params.put(key, value);
        return mBaseRequest;
    }

    public BaseRequest which(int which) {
        this.which = which;
        return mBaseRequest;
    }

    /**
     * 改用rxjava+retrofit封装实现
     * 
     * @return
     */
    public BaseRequest get() {
        if (isRequesting[which]) {
            return mBaseRequest;
        }
        isRequesting[which] = true;
        Log.d(TAG, "http:请求baseUrl:" + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpRequestService request = retrofit.create(HttpRequestService.class);
        // Call<String> call = null;
        // if (params.isEmpty()) {
        // Log.d(TAG, "http:无params的get方法调用");
        // call = request.get4GetBaseJson(path);
        // } else {
        // Log.d(TAG, "http:带params的get方法调用");
        // call = request.get4GetBaseJson(path, params);
        // }
        // call.enqueue(new Callback<String>() {
        // @Override
        // public void onResponse(Call<String> call, Response<String> response)
        // {
        // params.clear();
        // if (mBaseHttpCallback != null) {
        // Log.d(TAG, "http:get请求成功");
        // mBaseHttpCallback.onResult(response.body(), which);
        // }
        // }
        //
        // @Override
        // public void onFailure(Call<String> call, Throwable t) {
        // params.clear();
        // Log.d(TAG, "http:get请求失败\n错误信息：" + t.toString());
        // if (mBaseHttpCallback != null) {
        // mBaseHttpCallback.onError(which);
        // }
        // }
        // });

        final Observable<String> observable = request.get4GetBaseBean(path);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (mBaseHttpCallback != null) {
                            Log.d(TAG, "http:get请求成功");
                            mBaseHttpCallback.onResult(s, which);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
                        if (mBaseHttpCallback != null) {
                            mBaseHttpCallback.onError(which);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "http:get请求结束");
                        isRequesting[which] = false;
                    }
                });

        return mBaseRequest;

    }

    public BaseRequest post() {
        if (isRequesting[which]) {
            return mBaseRequest;
        }
        isRequesting[which] = true;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpRequestService request = retrofit.create(HttpRequestService.class);
        // Call<String> call = request.post4GetBaseJson(path, params);
        //
        // call.enqueue(new Callback<String>() {
        // @Override
        // public void onResponse(Call<String> call, Response<String> response)
        // {
        // params.clear();
        // if (mBaseHttpCallback != null) {
        // mBaseHttpCallback.onResult(response.body(), which);
        // }
        // }
        //
        // @Override
        // public void onFailure(Call<String> call, Throwable t) {
        // params.clear();
        // if (mBaseHttpCallback != null) {
        // mBaseHttpCallback.onError(which);
        // }
        // }
        // });

        Observable<String> observable = request.post4GetBaseBean(path);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (mBaseHttpCallback != null) {
                            Log.d(TAG, "http:get请求成功");
                            mBaseHttpCallback.onResult(s, which);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
                        if (mBaseHttpCallback != null) {
                            mBaseHttpCallback.onError(which);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mBaseRequest;
    }

    public BaseRequest callBack(BaseHttpCallBack mHttpCallback) {
        this.mBaseHttpCallback = mHttpCallback;
        return mBaseRequest;
    }
}
