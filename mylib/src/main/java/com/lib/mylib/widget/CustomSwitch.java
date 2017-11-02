
package com.lib.mylib.widget;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 自定义的switch<br/>
 * 备注：<br/>
 * 1.暂时不支持自定义xml属性配置，请在代码中先配置好颜色，初始状态等属性，<br/>
 * 2.xml中padding属性暂时不支持，margin可以<br/>
 * 3.xml中最好给定宽高<br/>
 * Created by homer on 16-6-11.<br/>
 * Update by qjj on 17-5-20
 */
public class CustomSwitch extends View
        implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {
    public final static int BACKGROUND_PAINT_STYLE_FILL = 0;
    public final static int BACKGROUND_PAINT_STYLE_STROKE = 1;
    private final static float DEFAULT_WIDTH_HEIGHT_PERCENT = 0.53f;// 默认宽高比
    private final static float FACE_ANIM_MAX_FRACTION = 1.4f;
    private final static float NORMAL_ANIM_MAX_FRACTION = 1.0f;
    private float mWidthAndHeightPercent;
    private float mWidth;
    private float mHeight;
    private float mPaddingLeft;
    private float mPaddingTop;
    private float mPaddingBottom;
    private float mPaddingRight;
    private float mTransitionLength;
    private Path mBackgroundPath;
    private Path mFacePath;

    // paint
    private int mOffBackgroundColor = 0xffcccccc;
    private int mOnBackgroundColor = 0xff33ccff;
    private int mCurrentColor = mOffBackgroundColor;
    // animation
    private ValueAnimator mValueAnimator;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private float mAnimationFraction = 0.0f;

    private int mSliderColor = 0xffffffff;// 滑块颜色
    private Paint mPaint;
    private float mSliderRadius;// 滑块的和背景的空隙大小
    private float mCenterX;
    private float mCenterY;

    private boolean mIsOpen = false;
    private boolean mIsDuringAnimation = false;

    private long mOnAnimationDuration = 250L;
    private long mOffAnimationDuration = (long) (mOnAnimationDuration * NORMAL_ANIM_MAX_FRACTION
            / FACE_ANIM_MAX_FRACTION);

    private OnStateChangeListener mOnStateChangeListener;// 状态变化监听

    // 新加的属性
    private Paint.Style backgroundPaintStyle = Paint.Style.FILL;
    private float strokeWidth = 0;// 空心模式下背景线条宽度
    private int mOffSliderColor = 0xffffffff;// 滑块关闭颜色
    private int mOnSliderColor = 0xffffffff;// 滑块打开颜色
    private int mCurrentSliderColor = mOffSliderColor;

    public CustomSwitch(Context context) {
        super(context);
        init(context);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mWidthAndHeightPercent = DEFAULT_WIDTH_HEIGHT_PERCENT;
        mPaint = new Paint();
        setState(false);
        // TODO: View ID 和 setSavedEnable都很重要的。
        setSaveEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * DEFAULT_WIDTH_HEIGHT_PERCENT);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // TODO：还有padding的问题
        mWidth = w;
        mHeight = h;
        // - strokeWidth是要预留线条位置
        float top = 0 + strokeWidth / 2;
        float left = 0 + strokeWidth / 2;
        float bottom = h * 1.0f - strokeWidth / 2; // 不预留画阴影了
        float right = w - strokeWidth / 2;

        RectF backgroundRecf = new RectF(left, top, bottom, bottom);
        mBackgroundPath = new Path();
        // TODO:???????????
        mBackgroundPath.arcTo(backgroundRecf, 90, 180);
        backgroundRecf.left = right - bottom;
        backgroundRecf.right = right;
        mBackgroundPath.arcTo(backgroundRecf, 270, 180);
        mBackgroundPath.close();

        float radius = (bottom / 2) * 0.85f;
        mCenterX = (top + bottom) / 2;
        mCenterY = (left + bottom) / 2;
        mSliderRadius = radius;
        mTransitionLength = right - bottom;

        RectF faceRecf = new RectF(mCenterX - mSliderRadius + (strokeWidth / 2),
                mCenterY - mSliderRadius + (strokeWidth / 2),
                mCenterX + mSliderRadius - (strokeWidth / 2),
                mCenterY + mSliderRadius - (strokeWidth / 2));
        mFacePath = new Path();
        mFacePath.arcTo(faceRecf, 90, 180);
        mFacePath.arcTo(faceRecf, 270, 180);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawForeground(canvas);
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(backgroundPaintStyle);
        mPaint.setAntiAlias(true);
        if (backgroundPaintStyle == Paint.Style.STROKE) {
            mPaint.setStrokeWidth(strokeWidth);
        }
        canvas.drawPath(mBackgroundPath, mPaint);
        mPaint.reset();
    }

    /**
     * 绘制前景(滑块等)
     *
     * @param canvas
     */
    private void drawForeground(Canvas canvas) {
        // 移动画布
        canvas.save();
        canvas.translate(getForegroundTransitionValue(), 0);
        drawSlider(canvas, mAnimationFraction);
        canvas.restore();
    }

    /**
     * 绘制滑块
     *
     * @param canvas
     * @param fraction
     */
    public void drawSlider(Canvas canvas, float fraction) {
        mPaint.setAntiAlias(true);
        // 圆形背景
        mPaint.setColor(mCurrentSliderColor);
        // mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mFacePath, mPaint);
    }

    private float getForegroundTransitionValue() {
        float result;
        if (mIsOpen) {
            if (mIsDuringAnimation) {
                result = mAnimationFraction > NORMAL_ANIM_MAX_FRACTION ? mTransitionLength
                        : mTransitionLength * mAnimationFraction;
            } else {
                result = mTransitionLength;
            }
        } else {
            if (mIsDuringAnimation) {
                result = mTransitionLength * mAnimationFraction;
            } else {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Log.e("TEST", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                if (mIsDuringAnimation) {
                    return true;
                }
                if (mIsOpen) {
                    startCloseAnimation();
                    mIsOpen = false;
                } else {
                    startOpenAnimation();
                    mIsOpen = true;
                }
                if (mOnStateChangeListener != null) {// 状态变化事件
                    mOnStateChangeListener.onStateChange(this, mIsOpen);
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 开始打开动画
     */
    private void startOpenAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0.0f, FACE_ANIM_MAX_FRACTION);
        mValueAnimator.setDuration(mOnAnimationDuration);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.start();
        startColorAnimation();
    }

    /**
     * 开始关闭动画
     */
    private void startCloseAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(NORMAL_ANIM_MAX_FRACTION, 0);
        mValueAnimator.setDuration(mOffAnimationDuration);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.start();
        startColorAnimation();
    }

    /**
     * 开始颜色渐变动画
     */
    private void startColorAnimation() {
        long duration = mIsOpen ? mOffAnimationDuration : mOnAnimationDuration;

        // 背景动画
        int colorFrom = mIsOpen ? mOnBackgroundColor : mOffBackgroundColor; // mIsOpen为true则表示要启动关闭的动画
        int colorTo = mIsOpen ? mOffBackgroundColor : mOnBackgroundColor;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom,
                colorTo);
        colorAnimation.setInterpolator(mInterpolator);
        colorAnimation.setDuration(duration); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mCurrentColor = (int) animator.getAnimatedValue();
            }

        });
        colorAnimation.start();

        // 滑块动画
        int sliderColorFrom = mIsOpen ? mOnSliderColor : mOffSliderColor;
        int sliderColorTo = mIsOpen ? mOffSliderColor : mOnSliderColor;
        ValueAnimator sliderColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                sliderColorFrom,
                sliderColorTo);
        sliderColorAnimation.setInterpolator(mInterpolator);
        sliderColorAnimation.setDuration(duration); // milliseconds
        sliderColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mCurrentSliderColor = (int) animator.getAnimatedValue();
            }

        });
        sliderColorAnimation.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // Log.e("TEST", animation.getAnimatedValue() + " ");
        mAnimationFraction = (float) animation.getAnimatedValue();
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        mIsDuringAnimation = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mIsDuringAnimation = false;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        mIsDuringAnimation = false;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        mIsDuringAnimation = true;
    }

    /**
     * 设置默认状态
     *
     * @param open - 默认状态
     */
    public void setState(boolean open) {
        mIsOpen = open;
        refreshState();
    }

    /**
     * 改变状态
     *
     * @param open
     */
    public void changeState(boolean open) {
        if (mIsDuringAnimation) {// 动画执行过程中不能改变状态
            return;
        }
        if (mIsOpen == open) {
            return;
        }
        if (open) {
            startOpenAnimation();
        } else {
            startCloseAnimation();
        }
        mIsOpen = open;// 先把动画执行完再改变状态，动画执行里需要用到这个状态
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onStateChange(this, mIsOpen);
        }
    }

    /**
     * 刷新状态
     */
    public void refreshState() {
        mCurrentColor = mIsOpen ? mOnBackgroundColor : mOffBackgroundColor;
        mCurrentSliderColor = mIsOpen ? mOnSliderColor : mOffSliderColor;
        invalidate();
    }

    /**
     * 设置背景是填充模式还是线条模式
     */
    public void setBackgroundPaintStyle(Paint.Style backgroundPaintStyle) {
        this.backgroundPaintStyle = backgroundPaintStyle;
        invalidate();
    }

    /**
     * 背景是线条模式时，设置背景线条大小<br/>
     * 注意：要在线条模式下设置
     *
     * @param strokeWidth 线条模式下线条宽度
     */
    public void setBackgroundStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    /**
     * 设置打开状态滑块的颜色
     *
     * @param color
     */
    public void setOpenSliderColor(@ColorInt int color) {
        mOnSliderColor = color;
        invalidate();
    }

    /**
     * 设置关闭状态滑块的颜色
     *
     * @param color
     */
    public void setCloseSliderColor(@ColorInt int color) {
        mOffSliderColor = color;
        invalidate();
    }

    /**
     * 设置打开状态的背景颜色
     *
     * @param color 颜色值代码
     */
    public void setOpenBackgroundColor(@ColorInt int color) {
        mOnBackgroundColor = color;
        invalidate();
    }

    /**
     * 设置关闭状态的背景颜色
     *
     * @param color 颜色值
     */
    public void setCloseBackgroundColor(@ColorInt int color) {
        mOffBackgroundColor = color;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        // Log.e("TEST", "onSave");
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.isOpen = mIsOpen ? 1 : 0;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // Log.e("TEST", "onRestore");
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(state);
        boolean result = ss.isOpen == 1;
        setState(result);
    }

    static class SavedState extends BaseSavedState {
        int isOpen;

        public SavedState(Parcel source) {
            super(source);
            isOpen = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isOpen);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[0];
            }
        };
    }

    /**
     * 设置状态变化监听
     *
     * @param mOnStateChangeListener
     */
    public void setOnStateChangeListener(OnStateChangeListener mOnStateChangeListener) {
        if (mOnStateChangeListener != null) {
            this.mOnStateChangeListener = mOnStateChangeListener;
        }
    }

    /**
     * 状态变化监听回调
     */
    public interface OnStateChangeListener {
        /**
         * 状态变化监听回调
         *
         * @param sv 当前控件
         * @param isOpen 状态
         */
        void onStateChange(CustomSwitch sv, boolean isOpen);
    }
}
