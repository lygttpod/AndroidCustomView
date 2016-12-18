package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.content.ContentValues.TAG;

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
    private int mWaveLength = 1000;


    /**
     * 屏幕宽度内波浪显示几个周期
     */
    private int mWaveCount;

    /**
     * 波纹的中间轴（基准线）
     */
    private int mCenterY;
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
    private int mWaveAmplitude = 25;

    private ValueAnimator valueAnimator;


    private boolean isStart = true;

    public WaveViewByBezier(Context context) {
        super(context);

    }

    public WaveViewByBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public WaveViewByBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {
        initPaint();

        initAnimation();

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;

//        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
        mWaveCount = 1;
        Log.d(TAG, "onSizeChanged: =======" + mWaveCount);
        mCenterY = mScreenHeight * 3 / 4;

        setOnClickListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWaveLength = getWidth();
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

        mWavePath.moveTo(mOffset, mCenterY);

        for (int i = 0; i < mWaveCount; i++) {
            mWavePath.quadTo(getWidth() /4+ mOffset, mCenterY + 60, getWidth() / 2 + mOffset, mCenterY);
            mWavePath.quadTo(getWidth() *3/ 4 + mOffset, mCenterY - 60, getWidth()+ mOffset, mCenterY);
        }
        mWavePath.lineTo(mScreenWidth, mScreenHeight);
        mWavePath.lineTo(0, mScreenHeight);
        mWavePath.close();
        canvas.drawPath(mWavePath, mWavePaint);
    }


    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, getWidth());
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
        valueAnimator.start();
    }

    public void startAnimation() {
        isStart = true;
        if (valueAnimator != null) {

            valueAnimator.reverse();
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
            onAnimationStart();
        }
    }


    @Override
    public void onClick(View v) {
        if (isStart){
            pauseAnimation();
        }else {
            resumeAnimation();
        }
    }
}
