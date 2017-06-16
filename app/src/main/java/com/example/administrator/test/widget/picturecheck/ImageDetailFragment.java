
package com.example.administrator.test.widget.picturecheck;

import static com.example.administrator.test.widget.picturecheck.BigPictureActivity.EXTRA_IMAGE_URLS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.administrator.test.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {

    View v;
    private SmoothImageView imageView;
    private ProgressBar progressBar;

    private PhotoViewAttacher mAttacher;

    private String mImageUrl;
    private ArrayList<String> mDatas;
    private int showPosition;// 要显示的图片的position
    private int mPosition;// 当前fragment的position
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;

    public static ImageDetailFragment newInstance(int position, String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putInt("position", position);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        mPosition = getArguments() != null ? getArguments().getInt("position") : 0;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        // mImageView = (ImageView) v.findViewById(R.id.image);
        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        initImageView();
        return v;
    }

    /**
     *
     */
    private void initImageView() {
        mDatas = (ArrayList<String>) getActivity().getIntent()
                .getSerializableExtra(EXTRA_IMAGE_URLS);
        showPosition = getActivity().getIntent().getIntExtra("position", 0);

        mLocationX = getActivity().getIntent().getIntExtra("locationX", 0);
        mLocationY = getActivity().getIntent().getIntExtra("locationY", 0);
        mWidth = getActivity().getIntent().getIntExtra("width", 0);
        mHeight = getActivity().getIntent().getIntExtra("height", 0);

        imageView = (SmoothImageView) v.findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.getInstance().displayImage(mDatas.get(mPosition),
                imageView);
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == SmoothImageView.STATE_TRANSFORM_IN) {
                    imageView
                            .setLayoutParams(new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // 动画执行完后再绑定手势操作
                    mAttacher = new PhotoViewAttacher(imageView);

                    mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

                        @Override
                        public void onPhotoTap(View arg0, float arg1, float arg2) {
                            imageView.transformOut();
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            ImageLoader.getInstance().displayImage(mDatas.get(mPosition),
                                    imageView);
                        }

                        @Override
                        public void onOutsidePhotoTap() {

                        }
                    });
                    progressBar.setVisibility(View.GONE);
                } else {
                    Intent it = new Intent();
                    it.putExtra("currentPosition", mPosition);
                    getActivity().setResult(1, it);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // ImageLoader.getInstance().displayImage(mImageUrl, imageView,
        // new SimpleImageLoadingListener() {
        // @Override
        // public void onLoadingStarted(String imageUri, View view) {
        // progressBar.setVisibility(View.VISIBLE);
        // }
        //
        // @Override
        // public void onLoadingFailed(String imageUri, View view, FailReason
        // failReason) {
        // String message = null;
        // switch (failReason.getType()) {
        // case IO_ERROR:
        // message = "下载错误";
        // break;
        // case DECODING_ERROR:
        // message = "图片无法显示";
        // break;
        // case NETWORK_DENIED:
        // message = "网络有问题，无法下载";
        // break;
        // case OUT_OF_MEMORY:
        // message = "图片太大无法显示";
        // break;
        // case UNKNOWN:
        // message = "未知的错误";
        // break;
        // }
        // Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        // progressBar.setVisibility(View.GONE);
        // }
        //
        // @Override
        // public void onLoadingComplete(String imageUri, View view, Bitmap
        // loadedImage) {
        // progressBar.setVisibility(View.GONE);
        // mAttacher.update();
        // }
        // });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", data.getIntExtra("currentPosition", 0) + "");
    }
}
