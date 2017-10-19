
package com.example.administrator.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.annotation.Injection;
import com.example.administrator.test.annotation.ViewInject;
import com.example.administrator.test.presenter.IMeiziPresenter;
import com.example.administrator.test.presenter.MeiziPresenter;
import com.example.administrator.test.view.IMeiziView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ConstraintTestActivity extends AppCompatActivity
        implements IMeiziView, View.OnClickListener {
    @ViewInject(R.id.act_constraint_test_iv_meizi)
    ImageView ivMeizi;
    @ViewInject(R.id.act_constraint_test_tv_date)
    TextView tvPublishDate;
    @ViewInject(R.id.act_constraint_test_btn_next)
    Button btnNext;
    @ViewInject(R.id.act_constraint_test_tv_left)
    TextView tvLeft;
    @ViewInject(R.id.act_constraint_test_tv_right)
    TextView tvRight;

    DisplayImageOptions options;

    IMeiziPresenter presenter;

    private final String TYPE_FULI = "福利";

    private int limit, page;
    private String TAG = "ConstraintTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_test);
        Injection.inject(this);
        // Create global configuration and initialize ImageLoader with this
        // config
        ImageLoaderConfiguration config = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(config);
        // 显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        limit = 10;
        page = 1;

        initPresenter();
        initView();
    }

    private void initPresenter() {
        presenter = new MeiziPresenter(this);
    }

    @Override
    public void setTitle(String text) {

    }

    @Override
    public void setTitleTextColor(@ColorRes int color) {

    }

    @Override
    public void showToast(String toast, int duration) {
        switch (duration) {
            case Toast.LENGTH_SHORT:
                Toast.makeText(ConstraintTestActivity.this, toast, Toast.LENGTH_SHORT).show();
                break;
            case Toast.LENGTH_LONG:
                Toast.makeText(ConstraintTestActivity.this, toast, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void changeText(String text) {

    }

    @Override
    public void setImage(String url) {
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, ivMeizi, options);
        }
    }

    @Override
    public void setDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            tvPublishDate.setText(date);
        }
    }

    private void initView() {
        // presenter.loadMeiziImgs(TYPE_FULI, String.valueOf(limit),
        // String.valueOf(page));
        presenter.loadMeiziImgsTest(TYPE_FULI, limit, page);
        btnNext.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        ivMeizi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_constraint_test_btn_next:
                presenter.nextMeizi();
                break;
            case R.id.act_constraint_test_tv_left:
                presenter.loadPreviousGroupOfMeizi();
                break;
            case R.id.act_constraint_test_tv_right:
                presenter.loadNextGroupOfMeizi();
                break;
            case R.id.act_constraint_test_iv_meizi:
                final int[] location = new int[2];
                ivMeizi.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标，含toolBar
                ivMeizi.getLocationOnScreen(location); // 获取在整个屏幕内的绝对坐标，含statusBar
                presenter.showBigPic(this, location[0],
                        location[1],
                        ivMeizi.getWidth(), ivMeizi.getHeight());
        }
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            int currentImgPosition = data.getIntExtra("currentPosition", 0);
            presenter.loadMeiziByPosition(currentImgPosition);
        }
    }
}
