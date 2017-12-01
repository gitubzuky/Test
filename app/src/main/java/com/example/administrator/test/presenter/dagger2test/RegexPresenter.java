package com.example.administrator.test.presenter.dagger2test;

import android.widget.Toast;

import com.example.administrator.test.view.IBaseView;
import com.example.administrator.test.view.daggertest.IDagger2TestView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/30.
 */
public class RegexPresenter extends Dagger2TestPresenter {
    public RegexPresenter(IDagger2TestView dagger2TestView) {
        super(dagger2TestView);
    }

    @Override
    public void doOnBrew(String status) {

    }

    /**
     * 获取正则表达式替换后的文本
     *
     * @param content 源文本
     * @param regex 正则表达式
     * @param replaceText 替换后的文本
     * @return
     */
    public String getTextAfterReplace(String content, String regex, String replaceText) {
        // 正则
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);

        // 返回结果
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            dagger2TestView.showToast(m.group(), Toast.LENGTH_SHORT);
            // 替换为 -
            m.appendReplacement(result, replaceText);
        }
        // 替换之前匹配到的内容
        m.appendTail(result);
        return result.toString();
    }
}
