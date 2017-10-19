
package com.lib.mylib.httptest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lib.mylib.bean.BaseBean;

/**
 * Created by Administrator on 2017/9/8.
 */

public class BaseParse implements JsonParse {
    @Override
    public BaseBean parseToBaseBean(String baseJson) {
        Gson gson = new Gson();
        BaseBean baseBean = new BaseBean();
        try {
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(baseJson);

            boolean isError = el.getAsJsonObject().get("error").getAsBoolean();
            baseBean.setError(isError);

            String dataJson = el.getAsJsonObject().get("results").toString();
            Log.d("http", "onResult:dealData" + dataJson);
            baseBean.setResults(dataJson);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return baseBean;
    }

    @Override
    public BaseBean parseToModelBean(String baseJson) {
        return null;
    }
}
