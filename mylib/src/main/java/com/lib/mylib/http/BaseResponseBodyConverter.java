
package com.lib.mylib.http;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 基础Response响应体类<br/>
 * 参考：http://blog.csdn.net/gengqiquan/article/details/52473334 <br/>
 * Created by qiujj on 2017/3/9.
 */

public class BaseResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            // Log.d("http", "data:" + value.string());
            // 切记：value.string()不能调用两次，上面那行去掉注释就会报错java.lang.IllegalStateException:closed
            return value.string();
        } finally {
            value.close();
        }
    }
}
