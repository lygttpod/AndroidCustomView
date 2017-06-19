package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
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

public class WaveViewByBezier extends View {

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
     * 一个屏幕内显示几个周期
     */
    private int mWaveCount;

    /**
     * 振幅
     */
    private int mWaveAmplitude;

    /**
     * 波形的颜色
     */
    private int waveColor = 0xaaFF7E37;

    private static final int SIN = 0;
    private static final int COS = 1;
    private static final int DEFAULT = SIN;

    private int waveType = DEFAULT;

    private ValueAnimator valueAnimator;


    public WaveViewByBezier(Context context) {
        this(context, null);

    }

    public WaveViewByBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mWaveAmplitude = dp2px(10);
        mWaveLength = dp2px(500);
        initPaint();
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        mWavePath = new Path();

        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(waveColor);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePaint.setAntiAlias(true);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenHeight = h;
        mScreenWidth = w;

        /**
         * 加上1.5是为了保证至少有两个波形（屏幕外边一个完整的波形，屏幕里边一个完整的波形）
         */
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (waveType) {
            case SIN:
                drawSinPath(canvas);
                break;
            case COS:
                drawCosPath(canvas);
                break;
        }

    }

    /**
     * sin函数图像的波形
     *
     * @param canvas
     */
    private void drawSinPath(Canvas canvas) {
        mWavePath.reset();

        mWavePath.moveTo(-mWaveLength + mOffset, mWaveAmplitude);

        // TODO: 2017/6/19   //相信很多人会疑惑为什么控制点的纵坐标是以下值,是根据公式计算出来的,具体计算方法情况文章内容

        for (int i = 0; i < mWaveCount; i++) {

            //第一个控制点的坐标为(-mWaveLength * 3 / 4,-mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i * mWaveLength,
                    -mWaveAmplitude,
                    -mWaveLength / 2 + mOffset + i * mWaveLength,
                    mWaveAmplitude);

            //第二个控制点的坐标为(-mWaveLength / 4,3 * mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i * mWaveLength,
                    3 * mWaveAmplitude,
                    mOffset + i * mWaveLength,
                    mWaveAmplitude);
        }

        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();

        canvas.drawPath(mWavePath, mWavePaint);
    }

    /**
     * cos函数图像的波形
     *
     * @param canvas
     */
    private void drawCosPath(Canvas canvas) {
        mWavePath.reset();

        mWavePath.moveTo(-mWaveLength + mOffset, mWaveAmplitude);

        for (int i = 0; i < mWaveCount; i++) {

            //第一个控制点的坐标为(-mWaveLength * 3 / 4,3 * mWaveAmplitude
            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i * mWaveLength,
                    3 * mWaveAmplitude,
                    -mWaveLength / 2 + mOffset + i * mWaveLength,
                    mWaveAmplitude);

            //第二个控制点的坐标为(-mWaveLength / 4,-mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i * mWaveLength,
                    -mWaveAmplitude,
                    mOffset + i * mWaveLength,
                    mWaveAmplitude);
        }

        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();

        canvas.drawPath(mWavePath, mWavePaint);
    }


    /**
     * 波形动画
     */
    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        valueAnimator.setDuration(2000);
        valueAnimator.setStartDelay(300);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void startAnimation() {
        initAnimation();
    }

    public void stopAnimation() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public void pauseAnimation() {
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
    }

    public void resumeAnimation() {
        if (valueAnimator != null) {
            valueAnimator.resume();
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
