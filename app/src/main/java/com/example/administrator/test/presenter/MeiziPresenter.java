
package com.example.administrator.test.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.test.model.MeiziTestBean.ResultsBean;
import com.example.administrator.test.view.IMeiziView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lib.mylib.http.BaseRequest;
import com.lib.mylib.http.HttpCallBack;

import java.util.List;

/**
 * 妹子图片相关逻辑处理 Created by Administrator on 2017/2/10.
 */

public class MeiziPresenter implements IMeiziPresenter {
    private static final String TAG = "MeiziPresenter";
    // private MeiziResult result = new MeiziResult();
    private IMeiziView ivConstraintTest;

    private List<ResultsBean> meiziList;

    private int position = 0;

    public MeiziPresenter(IMeiziView ivConstraintTest) {
        this.ivConstraintTest = ivConstraintTest;
    }

    /**
     * 测试使用自己封装的Retrofit来请求网络数据
     * 
     * @param type
     * @param limit
     * @param page
     */
    @Override
    public void loadMeiziImgsTest(String type, String limit, String page) {
        // 自己封装的Retrofit请求
        BaseRequest.getInstance()
                .baseUrl("http://gank.io/api/data/")
                .path(type + "/" + limit + "/" + page)
                .callBack(new HttpCallBack() {
                    @Override
                    public void onResult(String baseJson, int which) {
                        Log.d(TAG, "http请求成功，数据：" + baseJson);
                        dealData(baseJson);
                    }

                    @Override
                    public void onError(int which) {
                    }
                })
                .get();
    }

    /**
     * 处理数据
     * 
     * @param baseJson
     */
    private void dealData(String baseJson) {
        if (!TextUtils.isEmpty(baseJson)) {
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(baseJson);
            String datajson = el.getAsJsonObject().get("results").getAsJsonArray()
                    .toString();
            Log.d("http", "onResult:dealData" + datajson);
            Gson gson = new Gson();
            meiziList = gson.fromJson(datajson,
                    new TypeToken<List<ResultsBean>>() {
                    }.getType());

            if (meiziList != null && meiziList.size() > 0
                    && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                ivConstraintTest.setImage(meiziList.get(0).getUrl());
                ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
            } else {
                ivConstraintTest.showToast("图片加载出错", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void nextMeizi() {
        position++;
        if (meiziList != null && position < meiziList.size()
                && !TextUtils.isEmpty(meiziList.get(position).getUrl())) {
            ivConstraintTest.setImage(meiziList.get(position).getUrl());
            ivConstraintTest.setDate(meiziList.get(position).getPublishedAt());
        } else {
            ivConstraintTest.showToast("本批Meizi浏览完毕，再次点击重新播放", Toast.LENGTH_SHORT);
            position = -1;
        }
    }

    @Override
    public void loadDate() {
        if (meiziList != null && position < meiziList.size()
                && !TextUtils.isEmpty(meiziList.get(position).getPublishedAt())) {
            ivConstraintTest.setDate(meiziList.get(position).getPublishedAt());
        } else {
            position = -1;
        }
    }
}
