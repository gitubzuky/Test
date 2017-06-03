
package com.lib.mylib.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 基础Response响应体类<br/>
 * 参考：http://blog.csdn.net/gengqiquan/article/details/52473334 <br/>
 * Created by qiujj on 2017/3/9.
 */

public class ModelResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    String dataKey;// json中数据字段的key值
    Type type;

    public ModelResponseBodyConverter(String dataKey, Type type) {
        this.dataKey = dataKey;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            // Log.d("http", "data:" + value.string());
            // 切记：value.string()不能调用两次，上面那行去掉注释就会报错java.lang.IllegalStateException:closed

            String baseJson = value.string();
            // JsonParser parser = new JsonParser();
            // JsonElement el = parser.parse(baseJson);
            // String datajson =
            // el.getAsJsonObject().get(dataKey).getAsJsonArray()
            // .toString();
            // Log.d("http", "onResult:dealData" + datajson);
            Gson gson = new Gson();
            T beanList = gson.fromJson(baseJson, type);
            return beanList;

        } finally {
            value.close();
        }
    }

}
