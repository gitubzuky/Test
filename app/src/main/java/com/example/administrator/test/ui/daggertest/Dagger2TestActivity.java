
package com.example.administrator.test.ui.daggertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.example.administrator.test.presenter.dagger2test.Dagger2TestPresenter;
import com.example.administrator.test.presenter.dagger2test.IDagger2TestPresenter;
import com.example.administrator.test.presenter.dagger2test.RegexPresenter;
import com.example.administrator.test.view.daggertest.IDagger2TestView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import kotlin.text.Regex;

/**
 * <p>
 * Description: Dagger2测试页面
 * </p>
 * <p>
 * Tips:
 * </p>
 * <p>
 * Version: 1.0
 * </p>
 * <p>
 * Created by Zuky on 2017/11/24.
 * </p>
 */
public class Dagger2TestActivity extends AppCompatActivity
        implements IDagger2TestView, View.OnClickListener {

    @BindView(R.id.act_dagger2_test_tv_sample1)
    TextView tvSample;

    CoffeeShop coffeeShop;

    CoffeeMaker maker;

    IDagger2TestPresenter presenter;
    RegexPresenter regexPresenter;
    @BindView(R.id.act_dagger2_test_tv_content)
    TextView tvContent;
    @BindView(R.id.act_dagger2_test_et_regex)
    EditText etRegex;
    @BindView(R.id.act_dagger2_test_et_replace_text)
    EditText etReplaceText;
    @BindView(R.id.act_dagger2_test_btn_replace)
    Button btnReplace;
    @BindView(R.id.act_dagger2_test_tv_replace_text)
    TextView tvReplaceText;
    @BindView(R.id.act_dagger2_test_tv_current_status)
    TextView tvCurrentStatus;

    private long preTime;// 保存当前时间戳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2_test);
        ButterKnife.bind(this);
        tvSample.setOnClickListener(this);
        btnReplace.setOnClickListener(this);
        initPresenter();
        init();
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        presenter = new Dagger2TestPresenter(this);
        regexPresenter = new RegexPresenter(this);
    }

    /**
     * 初始化咖啡店例子
     */
    private void init() {
        coffeeShop = DaggerCoffeeShop.builder().build();
        maker = coffeeShop.maker();
        preTime = System.currentTimeMillis();
    }

    @Override
    public void showToast(String toast, int duration) {
        Toast.makeText(this, toast, duration).show();
    }

    @Override
    public void changeCoffeeMakerStatus(String status) {
        tvCurrentStatus.setText(status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_dagger2_test_btn_replace:
                try {
                    tvReplaceText.setText("替换后的文本：" + regexPresenter.getTextAfterReplace(
                            tvContent.getText().toString().replace("源文本：", ""),
                            etRegex.getText().toString(), etReplaceText.getText().toString()));
                } catch (Exception e) {
                    tvReplaceText.setText("错误：" + e.toString());
                }
                break;
            case R.id.act_dagger2_test_tv_sample1:
                maker.brew(new DisposableObserver<String>() {

                    @Override
                    public void onNext(String s) {
                        Log.e("rxjava-coffee", (System.currentTimeMillis() - preTime)+"s");
                        presenter.doOnBrew(s);
                        preTime = System.currentTimeMillis();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        maker.offMaker();
    }
}
