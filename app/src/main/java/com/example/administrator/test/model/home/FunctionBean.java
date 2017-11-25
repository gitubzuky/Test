
package com.example.administrator.test.model.home;

/**
 * Created by Zuky on 2016/9/13.
 * <p>Description: 首页面功能bean</p>
 * <p>Tips: </p>
 * <p>Version: 1.0</p>
 * <p>Update by Zuky on 2017/11/25.</p>
 */
public class FunctionBean {
    int type;// 界面类型
    String text;// 标题

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String title) {
        this.text = title;
    }
}
