
package com.lib.mylib.httptest;

import com.lib.mylib.bean.BaseBean;
import com.lib.mylib.http.BaseConverterFactory;
import com.lib.mylib.http.HttpRequestService;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Administrator on 2017/8/31.
 */

public class PostRequestBuilder implements RequestBuilder {

    private String baseUrl;
    private String path;
    private HashMap<String, String> params;
    private HttpCallback callback;
    private int which;

    private JsonParse parse;

    public PostRequestBuilder(JsonParse parse) {
        this.parse = parse;
    }

    @Override
    public void baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void path(String path) {
        this.path = path;
    }

    @Override
    public void params(HashMap<String, String> params) {
        this.params = params;
    }

    @Override
    public void callback(HttpCallback callback) {
        this.callback = callback;
    }

    @Override
    public void which(int which) {
        this.which = which;
    }

    @Override
    public void cashMode() {

    }

    @Override
    public void get() {
    }

    @Override
    public void post() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpRequestService<String> request = retrofit.create(HttpRequestService.class);

        Observable<String> observable = request.get4GetBaseBean(path);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, BaseBean>() {
                    @Override
                    public BaseBean apply(@NonNull String baseJson) throws Exception {
                        return parse.parseToBaseBean(baseJson);
                        // return parseJsonToBaseBean(baseJson, "dataKey", new
                        // TypeToken<BaseBean>() {
                        // }.getType());
                    }
                })
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        if (callback != null) {
                            if (!baseBean.isError()) {
                                callback.onResult(baseBean.getResults(),
                                        which);
                            } else {
                                callback.onError(which);
                            }
                            return;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
                        if (callback != null) {
                            callback.onError(which);
                        }
                        return;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
