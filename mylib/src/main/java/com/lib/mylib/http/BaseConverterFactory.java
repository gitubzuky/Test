
package com.lib.mylib.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 基础解析工厂类<br/>
 * Created by qiujj on 2017/3/9.
 */

public class BaseConverterFactory extends Converter.Factory {
    public static BaseConverterFactory create() {
        return new BaseConverterFactory();
    }

    public BaseConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
            Retrofit retrofit) {
        return new BaseResponseBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
            Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new BaseRequestBodyConverter();
    }
}
