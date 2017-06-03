
package com.lib.mylib.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

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

public class ModelConverterFactory extends Converter.Factory {
    private String dataKey;

    public static ModelConverterFactory create(String dataKey) {
        return new ModelConverterFactory(dataKey);
    }

    public ModelConverterFactory(String dataKey) {
        this.dataKey = dataKey;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
            Retrofit retrofit) {
        return new ModelResponseBodyConverter(dataKey, type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
            Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Gson gson = new Gson();
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ModelRequestBodyConverter<>(gson, adapter);
    }
}
