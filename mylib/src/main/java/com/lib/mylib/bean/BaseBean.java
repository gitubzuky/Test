
package com.lib.mylib.bean;

/**
 * Created by Administrator on 2017/3/25.
 */

public class BaseBean {
    private boolean error;
    private String resultCode;
    private String msg;
    private String results;// 数据json

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
