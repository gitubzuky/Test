
package com.example.administrator.test.widget.picturecheck;

import static com.example.administrator.test.widget.picturecheck.BigPictureActivity.EXTRA_IMAGE_INDEX;
import static com.example.administrator.test.widget.picturecheck.BigPictureActivity.EXTRA_IMAGE_URLS;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

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

    private long previousclickTime = 0;// 上一次点击时间
    // private long clickTimeInterval = 0;// 点击时间间隔

    private boolean isTransforming;// 动画是否在执行中
    private final String TAG = "ImageBigCheck";

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
        showPosition = getActivity().getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);// 点击的图片的position

        mLocationX = getActivity().getIntent().getIntExtra("locationX", 0);
        mLocationY = getActivity().getIntent().getIntExtra("locationY", 0);
        mWidth = getActivity().getIntent().getIntExtra("width", 0);
        mHeight = getActivity().getIntent().getIntExtra("height", 0);

        imageView = (SmoothImageView) v.findViewById(R.id.image);

        showBigPicture();

        Log.e(TAG, TAG + ": -> initImageView");
        imageView.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.getInstance().displayImage(mDatas.get(mPosition),
                imageView, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        Log.e(TAG, TAG + ": -> onLoadingStarted");
                        if (mPosition != showPosition) {
                            v.setBackgroundColor(Color.BLACK);
                        }
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // imageView.setAlpha(0.0f);
                        // v.setBackgroundColor(Color.parseColor("#00000000"));
                        Log.e(TAG, TAG + ": -> onLoadingComplete");
                        v.setBackgroundColor(Color.TRANSPARENT);
                        showBigPicture();
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
    }

    private void showBigPicture() {
        if (showPosition == mPosition) {
            // 执行放大到大图的动画
            imageView.setOriginalInfo(mWidth, mHeight, mLocationX,
                    mLocationY);
            imageView.transformIn();
        } else {
            bindPhotoView();// 没有放大到大图的过程动画，直接绑定手势缩放操作
        }
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformStart(int mode) {
                isTransforming = true;

                if (mode == SmoothImageView.STATE_TRANSFORM_IN) {
                    // 这里是从缩略图放大到大图的动画的开始，不用做其他操作
                    Log.e(TAG, TAG + ": -> onTransformStart_IN:isTransforming = "
                            + isTransforming);
                } else {
                    Log.e(TAG, TAG + ": -> onTransformStart_OUT:isTransforming = "
                            + isTransforming);
                    // 下面这一步操作是为了解决这一个bug
                    //
                    // 问题：
                    // 在图片缩小回原缩略图的过程中（注意是执行过渡动画的过程中），双击图片，会发生一个crash
                    // （把下面这个设置监听器的代码注释掉然后点击图片缩小回缩略图的过程中再双击一下就可以复现）
                    //
                    // 原因：
                    // 主要是在缩小回原缩略图的过程中再双击控件，会执行photoview的放大操作，
                    // 但是图片又在缩小，缩小的时候就会setScaleType(),而photoview是不允许绑定了imageview之后再次setScaleType()的，
                    // 所以就会发生crash(包括在缩小过程做任何有关photoview的操作都会出现crash)
                    //
                    // 解决:
                    // 所以只要在缩小动画开始之前将photoview所绑定的imageview清除掉就行了
                    // mAttacher.cleanup();
                    mAttacher.update();
                }
            }

            @Override
            public void onTransformComplete(int mode) {
                previousclickTime = System.currentTimeMillis();
                if (mode == SmoothImageView.STATE_TRANSFORM_IN) {
                    isTransforming = false;
                    Log.e(TAG, TAG + ": -> onTransformComplete_IN:isTransforming = "
                            + isTransforming);
                    // 放大的动画执行完后再绑定手势操作
                    bindPhotoView();
                } else {
                    Log.e(TAG, TAG + ": -> onTransformComplete_OUT");
                    Intent it = new Intent();
                    it.putExtra("currentPosition", mPosition);
                    getActivity().setResult(1, it);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                }
            }
        });
    }

    // 绑定手势缩放操作
    private void bindPhotoView() {
        mAttacher = new PhotoViewAttacher(imageView);

        mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

            /**
             * A callback to receive where the user taps on a photo. You will only receive a
             * callback if the user taps on the actual photo, tapping on 'whitespace' will
             * be ignored.
             *
             * @param view ImageView the user tapped.
             * @param x where the user tapped from the of the Drawable, as percentage of the
             *            Drawable width.
             * @param y where the user tapped from the top of the Drawable, as percentage of
             *            the
             */
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                Log.e(TAG, TAG + ": -> onPhotoTap");
                if (isTransforming) {
                    return;
                }
                imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
                imageView.transformOut();
            }

            // @Override
            // public void onOutsidePhotoTap() {
            // Log.e(TAG, TAG + ": -> onOutsidePhotoTap");
            // if (System.currentTimeMillis()
            // - previousclickTime < 500) {
            // return;
            // }
            // }
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isTransforming = false;
        Log.e(TAG, TAG
                + ": -> onDestroy:isTransforming = false");
        Log.e(TAG,
                "------------------------------------ finish --------------------------------------");
    }
}
