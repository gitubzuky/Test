
package com.lib.mylib.httptest;

import com.lib.mylib.bean.BaseBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface JsonParse<T> {
    BaseBean parseToBaseBean(String baseJson);

    T parseToModelBean(String baseJson);
}
