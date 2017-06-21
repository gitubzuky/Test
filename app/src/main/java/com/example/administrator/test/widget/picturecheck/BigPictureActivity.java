
package com.example.administrator.test.widget.picturecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.test.R;

import java.util.ArrayList;

public class BigPictureActivity extends AppCompatActivity {
    private int locationX, locationY, width, height;// x,y坐标和宽高

    private ArrayList<String> imgUrlList;

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "position";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private int position;
    Intent it;
    private final String TAG = "ImageBigCheck";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_picture);

        it = new Intent();// 回传给页面，setResult用的

        locationX = getIntent().getIntExtra("locationX", 0);
        locationY = getIntent().getIntExtra("locationY", 0);
        width = getIntent().getIntExtra("width", 0);
        height = getIntent().getIntExtra("height", 0);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        mPager = (HackyViewPager) findViewById(R.id.act_big_picture_vp);
        BigPictureActivity.ImagePagerAdapter mAdapter = new BigPictureActivity.ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(0);
        indicator = (TextView) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1,
                mPager.getAdapter().getCount());
        indicator.setText(text);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CharSequence text = getString(R.string.viewpager_indicator, position + 1,
                        mPager.getAdapter().getCount());
                indicator.setText(text);
                pagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // // 更新下标
        // mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        //
        // @Override
        // public void onPageScrollStateChanged(int arg0) {
        // }
        //
        // @Override
        // public void onPageScrolled(int arg0, float arg1, int arg2) {
        // }
        //
        // @Override
        // public void onPageSelected(int arg0) {
        // CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1,
        // mPager.getAdapter().getCount());
        // indicator.setText(text);
        // pagerPosition = arg0;
        //
        // }
        //
        // });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(position, url);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", data.getIntExtra("currentPosition", 0) + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
