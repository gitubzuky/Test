
package com.example.administrator.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义switch Created by Administrator on 2017/4/25.
 * @deprecated 废弃
 */

public class MyCustomSwitch extends View {
    Paint paintBg;// 背景
    Path path;
    RectF rect;
    Paint paintSlider;// 滑块

    int width, height;// 控件宽高

    int moveLenght = 0;

    boolean isAnimating = false;// 是否执行动画中
    boolean switchStuts = false;// switch当前的状态，false：关闭；true：开启

    private static final double PROPORTION = 1.8;// 控件宽高比：27:15 = 1.8

    Handler animHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {// 刷新界面
                invalidate();
            }
            return false;
        }
    });

    /**
     * 动画变量控制线程
     */
    Runnable animRunnable = new Runnable() {
        @Override
        public void run() {
            if (isAnimating) {
                return;
            }
            isAnimating = true;
            if (!switchStuts) {
                while (height + moveLenght <= width) {
                    moveLenght++;
                    animHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isAnimating = false;
                switchStuts = true;
            } else {
                while (height / 2 + moveLenght > height / 2) {
                    moveLenght--;
                    animHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isAnimating = false;
                switchStuts = false;
            }
        }
    };

    public MyCustomSwitch(Context context) {
        super(context);
    }

    public MyCustomSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //
        // switch (widthMode) {
        // case MeasureSpec.UNSPECIFIED:
        // Log.d("onMeasure", "widthMode - MeasureSpec.UNSPECIFIED");
        // break;
        // case MeasureSpec.AT_MOST:
        // Log.d("onMeasure", "widthMode - MeasureSpec.AT_MOST");
        // break;
        // case MeasureSpec.EXACTLY:
        // Log.d("onMeasure", "widthMode - MeasureSpec.EXACTLY");
        // break;
        // }
        //
        // switch (heightMode) {
        // case MeasureSpec.UNSPECIFIED:
        // Log.d("onMeasure", "heightMode - MeasureSpec.UNSPECIFIED");
        // break;
        // case MeasureSpec.AT_MOST:
        // Log.d("onMeasure", "heightMode - MeasureSpec.AT_MOST");
        // break;
        // case MeasureSpec.EXACTLY:
        // Log.d("onMeasure", "heightMode - MeasureSpec.EXACTLY");
        // break;
        // }
        //
        // this.height = heightSize;
        // this.width = widthSize;
        this.width = getMySize(100, widthMeasureSpec);
        this.height = getMySize(100, heightMeasureSpec);

        if (width / height < PROPORTION) {
            height = (int) (width / PROPORTION);
        } else {
            width = (int) (height * PROPORTION);
        }

        Log.d("onMeasure", "width = " + width);
        Log.d("onMeasure", "height = " + height);
        setMeasuredDimension(width, height);
        initPaint();
        initShape();
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {// 如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {// 如果测量模式是最大取值为size
                // 我们将大小取最大值,你也可以取其他值
                // mySize = Math.min(size, defaultSize);
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {// 如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    private void initPaint() {
        paintBg = new Paint();// 背景
        // 设置画笔基本属性
        paintBg.setAntiAlias(true);// 抗锯齿功能
        paintBg.setColor(Color.RED); // 设置画笔颜色
        paintBg.setStyle(Paint.Style.FILL);// 设置填充样式
        // Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paintBg.setStrokeWidth(5);// 设置画笔宽度
        // paintBg.setShadowLayer(10, 15, 15, Color.GREEN);// 设置阴影

        paintSlider = new Paint();// 滑块
        // 设置画笔基本属性
        paintSlider.setAntiAlias(true);// 抗锯齿功能
        paintSlider.setColor(Color.WHITE); // 设置画笔颜色
        paintSlider.setStyle(Paint.Style.FILL);// 设置填充样式
        // Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paintSlider.setStrokeWidth(5);// 设置画笔宽度
        // paintSlider.setShadowLayer(10, 15, 15, Color.WHITE);// 设置阴影
    }

    private void initShape() {
        path = new Path();
        rect = new RectF(0, 0, height, height);
        path.arcTo(rect, 90, 180);// 画左半圆弧
        path.lineTo(width - height / 2, 0);
        rect = new RectF(width - height, 0, width, height);
        path.arcTo(rect, 270, 180);// 画右半圆弧
        path.lineTo(height / 2, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置画布背景颜色
        canvas.drawRGB(255, 255, 255);
        canvas.drawPath(path, paintBg);// 画出路径

        canvas.save();
        canvas.drawCircle(height / 2 + moveLenght, height / 2, height / 2 - 6, paintSlider);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                new Thread(animRunnable).start();
                break;
        }
        return false;
    }
}
