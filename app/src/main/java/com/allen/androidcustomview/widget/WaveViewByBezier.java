package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * Created by allen on 2016/12/13.
 * <p>
 * 波浪动画
 */

public class WaveViewByBezier extends View implements View.OnClickListener {

    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 波浪的画笔
     */
    private Paint mWavePaint;
    /**
     * 一个周期波浪的长度
     */
    private int mWaveLength;

    /**
     * 波浪的路径
     */
    Path mWavePath;

    /**
     * 平移偏移量
     */
    private int mOffset;

    /**
     * 振幅
     */
    private int mWaveAmplitude = 50;

    private ValueAnimator valueAnimator;


    private boolean isStart = false;

    public WaveViewByBezier(Context context) {
        this(context, null);

    }

    public WaveViewByBezier(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WaveViewByBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mWaveAmplitude = dp2px(15);
        initPaint();
        setOnClickListener(this);

    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        mWavePath = new Path();

        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.GRAY);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }

    /**
     * 测量宽度
     *
     * @param mode
     * @param width
     * @return
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mScreenWidth = width;
                break;
        }
        return mScreenWidth;
    }

    /**
     * 测量高度
     *
     * @param mode
     * @param height
     * @return
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mScreenHeight = height;
                break;
        }
        return mScreenHeight;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWaveLength = mScreenWidth;
        mWavePath.reset();

//        mWavePath.moveTo(-mWaveLength + mOffset, mCenterY);
//
//        for (int i = 0; i < mWaveCount; i++) {
//            mWavePath.quadTo(-mWaveLength * 3 / 4 + i * mWaveLength + mOffset, mCenterY + 60, -mWaveLength / 2 + i * mWaveLength + mOffset, mCenterY);
//            mWavePath.quadTo(-mWaveLength / 4 + i * mWaveLength + mOffset, mCenterY - 60, i * mWaveLength + mOffset, mCenterY);
//        }
//        mWavePath.lineTo(mScreenWidth, mScreenHeight);
//        mWavePath.lineTo(0, mScreenHeight);
//        mWavePath.close();

        mWavePath.moveTo(-mWaveLength + mOffset, mWaveAmplitude);

        mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset, -mWaveAmplitude, -mWaveLength / 2 + mOffset, mWaveAmplitude);

        mWavePath.quadTo(-mWaveLength / 4 + mOffset, 3 * mWaveAmplitude, 0 + mOffset, mWaveAmplitude);

        mWavePath.quadTo(mWaveLength / 4 + mOffset, -mWaveAmplitude, mWaveLength / 2 + mOffset, mWaveAmplitude);

        mWavePath.quadTo(mWaveLength * 3 / 4 + mOffset, 3 * mWaveAmplitude, mWaveLength + mOffset, mWaveAmplitude);

        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();


//        mWavePath.moveTo(mOffset, mCenterY);
//
//        for (int i = 0; i < mWaveCount; i++) {
//            mWavePath.quadTo(mWaveLength /4+ mOffset, mCenterY + 60, mWaveLength / 2 + mOffset, mCenterY);
//            mWavePath.quadTo(mWaveLength *3/ 4 + mOffset, mCenterY - 60,  + mOffset, mCenterY);
//        }
//        mWavePath.lineTo(mScreenWidth, mScreenHeight);
//        mWavePath.lineTo(0, mScreenHeight);
//        mWavePath.close();
        canvas.drawPath(mWavePath, mWavePaint);
    }


    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, mScreenWidth);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    public void startAnimation() {
        isStart = true;
        if (valueAnimator != null) {

            valueAnimator.start();
        }
    }

    public void stopAnimation() {
        isStart = false;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.clearAnimation();
        }
    }

    public void pauseAnimation() {
        isStart = false;
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
    }

    public void resumeAnimation() {
        isStart = true;
        if (valueAnimator != null) {
            valueAnimator.resume();
        }
    }


    @Override
    public void onClick(View v) {
        initAnimation();
        if (valueAnimator != null) {
            if (isStart) {
                isStart = false;
                valueAnimator.pause();
            } else {
                isStart = true;
                valueAnimator.start();
            }
        }

    }


    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
