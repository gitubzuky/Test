
package com.example.administrator.test.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.test.model.MeiziTestBean;
import com.example.administrator.test.model.MeiziTestBean.ResultsBean;
import com.example.administrator.test.view.IMeiziView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lib.mylib.http.BaseBeanHttpCallBack;
import com.lib.mylib.http.BaseHttpCallBack;
import com.lib.mylib.http.BaseRequest;
import com.lib.mylib.http.HttpCallBack;
import com.lib.mylib.http.HttpRequestUtil;

import java.util.ArrayList;
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

    private int page = 1;
    private int limit = 10;
    private String dataType = "福利";

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
    public void loadMeiziImgsTest(String type, int limit, int page) {
        this.dataType = type;
        this.limit = limit;
        this.page = page;
        // request4DataUseBase_Model(dataType, limit, page);

        // request4BaseBeanUseBase_Model(dataType, limit, page);
        // request4BaseBeanUseModel(dataType, limit, page);
        request4DataByLibBaseBean(type, limit, page, new HttpCallBack<ResultsBean>() {
            @Override
            public void onResult(List<ResultsBean> dataList, int which) {
                meiziList = dataList;
                if (meiziList != null && meiziList.size() > 0
                        && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                    ivConstraintTest.setImage(meiziList.get(0).getUrl());
                    ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
                } else {
                    ivConstraintTest.showToast("图片加载出错", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public boolean filter(List<ResultsBean> dataList) {
                dataList.remove(5);
                dataList.remove(1);
                if (dataList.size() < 9) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public void onError(int errorCode, int which) {

            }

        });
    }

    /**
     * Base请求Base处理
     * 
     * @param type
     * @param limit
     * @param page
     */
    private void request4DataUseBase(String type, int limit, int page) {
        // 自己封装的Retrofit请求
        BaseRequest.getInstance()
                .baseUrl("http://gank.io/api/data/")
                .path(type + "/" + limit + "/" + page)
                .callBack(new BaseHttpCallBack() {
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
     * Base请求model解析
     * 
     * @param type
     * @param limit
     * @param page
     */
    private void request4DataUseBase_Model(String type, int limit, int page) {
        HttpRequestUtil util = new HttpRequestUtil<>();
        util.baseUrl("http://gank.io/api/data/")
                .path(type + "/" + limit + "/" + page)
                .callBack(new HttpCallBack<ResultsBean>() {
                    @Override
                    public void onResult(List<ResultsBean> dataList, int which) {
                        meiziList = dataList;
                        if (meiziList != null && meiziList.size() > 0
                                && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                            ivConstraintTest.setImage(meiziList.get(0).getUrl());
                            ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
                        } else {
                            ivConstraintTest.showToast("图片加载出错", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public boolean filter(List<ResultsBean> dataList) {
                        // dataList.remove(5);
                        // dataList.remove(1);
                        // if (dataList.size() < 9) {
                        // return true;
                        // } else {
                        // return false;
                        //
                        return false;
                    }

                    @Override
                    public void onError(int errorCode, int which) {

                    }
                })
                .modelGet("results", new TypeToken<List<ResultsBean>>() {
                }.getType());
    }

    /**
     * Base请求model解析 - 返回基础Bean
     *
     * @param type
     * @param limit
     * @param page
     */
    private void request4BaseBeanUseBase_Model(String type, int limit, int page) {
        meiziList = new ArrayList<>();
        HttpRequestUtil util = new HttpRequestUtil<>();
        util.baseUrl("http://gank.io/api/data/")
                .path(type + "/" + limit + "/" + page)
                .callBack(new BaseBeanHttpCallBack<MeiziTestBean>() {
                    @Override
                    public void onResult(MeiziTestBean bean, int which) {
                        if (!bean.isError()) {
                            meiziList.clear();
                            meiziList.addAll(bean.getResults());
                            if (meiziList != null && meiziList.size() > 0
                                    && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                                ivConstraintTest.setImage(meiziList.get(0).getUrl());
                                ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
                            } else {
                                ivConstraintTest.showToast("图片加载出错",
                                        Toast.LENGTH_SHORT);
                            }
                        }
                    }

                    @Override
                    public boolean filter(MeiziTestBean baseBean) {
                        return false;
                    }

                    @Override
                    public void onError(int which) {

                    }
                })
                .modelGet(new TypeToken<MeiziTestBean>() {
                }.getType());
    }

    /**
     * Mode请求和处理
     * 
     * @param type
     * @param limit
     * @param page
     */
    private void request4BaseBeanUseModel(String type, int limit, int page) {
        HttpRequestUtil<List<ResultsBean>> util = new HttpRequestUtil<>();
        util.baseUrl("http://gank.io/api/data/")
                .path(type + "/" + limit + "/" + page)
                .callBack(new HttpCallBack<ResultsBean>() {
                    @Override
                    public void onResult(List<ResultsBean> dataList, int which) {
                        meiziList = dataList;
                        if (meiziList != null && meiziList.size() > 0
                                && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                            ivConstraintTest.setImage(meiziList.get(0).getUrl());
                            ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
                        } else {
                            ivConstraintTest.showToast("图片加载出错", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public boolean filter(List<ResultsBean> dataList) {
                        dataList.remove(5);
                        dataList.remove(1);
                        if (dataList.size() < 9) {
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public void onError(int errorCode, int which) {

                    }

                })
                .modelGet(new TypeToken<MeiziTestBean>() {
                }.getType());
    }

    /**
     * 使用lib中的BaseBean获取数据
     *
     * @param type
     * @param limit
     * @param page
     */
    private void request4DataByLibBaseBean(String type, int limit, int page,
            HttpCallBack<ResultsBean> callBack) {
        HttpRequestUtil<List<ResultsBean>> util = new HttpRequestUtil<>();
        util.baseUrl("http://gank.io/api/data/")
                .path(type + "/" + limit + "/" + page)
                .callBack(callBack)
                .get("results", new TypeToken<List<ResultsBean>>() {
                }.getType());
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

    @Override
    public void loadNextGroupOfMeizi() {
        page++;
        request4DataByLibBaseBean(dataType, limit, page, new HttpCallBack<ResultsBean>() {
            @Override
            public void onResult(List<ResultsBean> dataList, int which) {
                meiziList = dataList;
                if (meiziList != null && meiziList.size() > 0
                        && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                    ivConstraintTest.setImage(meiziList.get(0).getUrl());
                    ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
                } else {
                    ivConstraintTest.showToast("图片加载出错", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public boolean filter(List<ResultsBean> dataBean) {
                return false;
            }

            @Override
            public void onError(int errorCode, int which) {

            }
        });
    }

    @Override
    public void loadPreviousGroupOfMeizi() {
        page--;
        if (page < 1) {
            page++;
            ivConstraintTest.showToast("已经是最新的一批Meizi了╮(╯▽╰)╭", Toast.LENGTH_SHORT);
            return;
        }
        position = -1;
        request4DataByLibBaseBean(dataType, limit, page, new HttpCallBack<ResultsBean>() {
            @Override
            public void onResult(List<ResultsBean> dataList, int which) {
                meiziList.clear();
                meiziList.addAll(dataList);
                if (meiziList != null && meiziList.size() > 0
                        && !TextUtils.isEmpty(meiziList.get(0).getUrl())) {
                    ivConstraintTest.setImage(meiziList.get(0).getUrl());
                    ivConstraintTest.setDate(meiziList.get(0).getPublishedAt());
                } else {
                    ivConstraintTest.showToast("图片加载出错", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public boolean filter(List<ResultsBean> dataBean) {
                return false;
            }

            @Override
            public void onError(int errorCode, int which) {

            }
        });
    }
}
