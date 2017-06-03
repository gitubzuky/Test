
package com.lib.mylib.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lib.mylib.bean.BaseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.Url;

/**
 * 有泛型不能使用单例模式<br/>
 * Created by qiujj on 2017/3/23.
 */
public class HttpRequestUtil<T> {
    // private static HttpRequestUtil mHttpRequest = new HttpRequestUtil();

    private static final String TAG = "HttpRequestUtil";
    private HttpCallBack<T> mHttpCallBack;
    private BaseBeanHttpCallBack<T> mBaseBeanHttpCallBack;

    public boolean[] isRequesting = {
            false, false, false, false, false, false, false, false, false, false
    };
    private int which = 0;
    private String baseUrl;
    private static Map<String, Object> params;
    private String path;

    private List<T> datalist;// 数据list

    // /**
    // * 饿汉单例
    // *
    // * @return
    // */
    // public static HttpRequestUtil getInstance() {
    // return mHttpRequest;
    // }

    public HttpRequestUtil() {
        datalist = new ArrayList<>();
    }

    public void baseGet() {
    }

    public HttpRequestUtil baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public HttpRequestUtil path(@Url String path) {
        this.path = path;
        return this;
    }

    public HttpRequestUtil param(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public HttpRequestUtil which(int which) {
        this.which = which;
        return this;
    }

    // /**
    // * 封装的请求 - 直接解析结果
    // *
    // * @param dataKey - json中数据字段的key值
    // * @param type
    // * @return
    // */
    //
    // public HttpRequestUtil modelGet(final String dataKey, final Type type) {
    // if (isRequesting[which]) {
    // return this;
    // }
    // isRequesting[which] = true;
    // datalist.clear();
    // Log.d(TAG, "http:请求baseUrl:" + baseUrl);
    // Retrofit retrofit = new Retrofit.Builder()
    // .baseUrl(baseUrl)
    // .addConverterFactory(BaseConverterFactory.create())
    // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    // .build();
    // HttpRequestService request = retrofit.create(HttpRequestService.class);
    //
    // final Observable<String> observable = request.get4GetBaseBean(path);
    // observable.subscribeOn(Schedulers.io())
    // .observeOn(AndroidSchedulers.mainThread())
    // .subscribe(new Observer<String>() {
    // @Override
    // public void onSubscribe(Disposable d) {
    //
    // }
    //
    // @Override
    // public void onNext(String baseJson) {
    // if (mHttpCallBack != null) {
    // Log.d(TAG, "http:get请求成功");
    // datalist.addAll(
    // (Collection<? extends T>) parseJsonToList(baseJson, dataKey,
    // type));
    // mHttpCallBack.onResult(datalist, which);
    // }
    // }
    //
    // @Override
    // public void onError(Throwable e) {
    // Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
    // if (mHttpCallBack != null) {
    // mHttpCallBack.onError(which);
    // }
    // }
    //
    // @Override
    // public void onComplete() {
    // Log.d(TAG, "http:get请求结束");
    // isRequesting[which] = false;
    // }
    // });
    // return this;
    // }

    /**
     * 封装的请求 - 直接解析结果(使用Map转换解析)
     *
     * @param dataKey - json中数据字段的key值
     * @param type
     * @return
     */

    public HttpRequestUtil modelGet(final String dataKey, final Type type) {
        if (isRequesting[which]) {
            return this;
        }
        isRequesting[which] = true;
        datalist.clear();
        Log.d(TAG, "http:请求baseUrl:" + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpRequestService request = retrofit.create(HttpRequestService.class);

        final Observable<String> observable = request.get4GetBaseBean(path);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, List<T>>() {
                    @Override
                    public List<T> apply(@NonNull String baseJson) throws Exception {
                        return parseJsonToList(baseJson, dataKey, type);
                    }
                })
                .filter(new Predicate<List<T>>() {
                    @Override
                    public boolean test(@NonNull List<T> list) throws Exception {
                        if (mHttpCallBack != null) {
                            return mHttpCallBack.filter(list);
                        } else {
                            return false;
                        }
                    }
                })
                .subscribe(new Observer<List<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<T> list) {
                        if (mHttpCallBack != null) {
                            Log.d(TAG, "http:get请求成功");
                            datalist.addAll(list);
                            mHttpCallBack.onResult(datalist, which);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
                        if (mHttpCallBack != null) {
                            mHttpCallBack.onError(which);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "http:get请求结束");
                        isRequesting[which] = false;
                    }
                });
        return this;
    }

    /**
     * 测试-自定义的解析工厂
     *
     * @param dataKey
     * @return
     */
    public HttpRequestUtil modelGet(final String dataKey) {
        if (isRequesting[which]) {
            return this;
        }
        isRequesting[which] = true;
        datalist.clear();
        Log.d(TAG, "http:请求baseUrl:" + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ModelConverterFactory.create(dataKey))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpRequestService<BaseBean> request = retrofit.create(HttpRequestService.class);

        Observable<BaseBean> observable = request.get4GetModelBean(path);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {

                        if (mBaseBeanHttpCallBack != null) {
                            Log.d(TAG, "http:get请求成功");
                            // mBaseBeanHttpCallBack.onResult(baseBean,
                            // which);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
                        if (mBaseBeanHttpCallBack != null) {
                            mBaseBeanHttpCallBack.onError(which);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }

    /**
     * 测试-自定义的解析工厂(不返回数据，返回基础bean)
     *
     * @return
     */
    public HttpRequestUtil modelGet(final Type type) {
        if (isRequesting[which]) {
            return this;
        }
        isRequesting[which] = true;
        datalist.clear();
        Log.d(TAG, "http:请求baseUrl:" + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HttpRequestService request = retrofit.create(HttpRequestService.class);

        Observable<String> observable = request.get4GetBaseBean(path);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String baseJson) throws Exception {
                        return parseJsonToBean(baseJson, type);
                    }
                })
                .filter(new Predicate<T>() {
                    @Override
                    public boolean test(@NonNull T t) throws Exception {
                        if (mBaseBeanHttpCallBack != null) {
                            return mBaseBeanHttpCallBack.filter(t);
                        } else {
                            return false;
                        }
                    }
                })
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull T baseBean) {
                        if (mBaseBeanHttpCallBack != null) {
                            Log.d(TAG, "http:get请求成功");
                            mBaseBeanHttpCallBack.onResult(baseBean, which);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "http:get请求失败\n错误信息：" + e.toString());
                        if (mBaseBeanHttpCallBack != null) {
                            mBaseBeanHttpCallBack.onError(which);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return this;
    }

    private <T> List<T> parseJsonToList(String json, String dataKey, Type type) {
        List<T> tempList = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(json);
            String datajson = el.getAsJsonObject().get(dataKey).getAsJsonArray()
                    .toString();
            Log.d("http", "onResult:dealData" + datajson);
            Gson gson = new Gson();
            tempList = gson.fromJson(datajson, type);
        }
        return tempList;
    }

    private <T> T parseJsonToBean(String baseJson, Type type) {
        Gson gson = new Gson();
        T baseBean = null;
        try {
            baseBean = gson.fromJson(baseJson, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return baseBean;
    }

    public HttpRequestUtil callBack(HttpCallBack<T> httpCallback) {
        this.mHttpCallBack = httpCallback;
        return this;
    }

    public HttpRequestUtil callBack(BaseBeanHttpCallBack<T> httpCallback) {
        this.mBaseBeanHttpCallBack = httpCallback;
        return this;
    }
}
