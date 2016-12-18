package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.allen.androidcustomview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2016/12/16.
 * /**
 * y=Asin(ωx+φ)+k
 * <p>
 * A—振幅越大，波形在y轴上最大与最小值的差值越大
 * ω—角速度， 控制正弦周期(单位角度内震动的次数)
 * φ—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变φ,达到波浪移动效果
 * k—偏距，反映在坐标系上则为图像的上移或下移。
 */

public class WaveViewBySinCos extends View {


    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 波纹的中间轴（基准线）
     */
    private int mCenterY;

    private int mWaveLength = 1000;

    private int mWaveCount;

    private int mOffset;

    private int mWaveNum = 4;

    private int A = 30;
    private int K = 100;

    private List<Path> mPaths = new ArrayList<>();
    private List<Paint> mPaints = new ArrayList<>();

    private float φ;
    double ω;

    /**
     * 渐变色shader
     */
    private Shader mShader1, mShader2;
    /**
     * 渐变色shade配置参数
     */
    private int colors1[] = new int[2];
    private int colors2[] = new int[2];


    public WaveViewBySinCos(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPath();
        initPaint();


        initAnimation();
    }

    private void initPath() {
        for (int i = 0; i < mWaveNum; i++) {
            Path path = new Path();
            mPaths.add(path);
        }
    }

    private void initPaint() {

        colors1[0] = getResources().getColor(R.color.wave_start1);
        colors1[1] = getResources().getColor(R.color.wave_end1);

        colors2[0] = getResources().getColor(R.color.wave_start2);
        colors2[1] = getResources().getColor(R.color.wave_end2);

        for (int i = 0; i < mWaveNum; i++) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaints.add(paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;

        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);

        ω = 2 * Math.PI / getWidth();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShader1 == null) {
            mShader1 = new LinearGradient(0, 0, getWidth(), 0, colors1, null, Shader.TileMode.CLAMP);
        }
        if (mShader2 == null) {
            mShader2 = new LinearGradient(0, 0, getWidth(), 0, colors2, null, Shader.TileMode.CLAMP);
        }
//        φ -= 0.1f;
        φ -= 0.03f;
        float y;

        for (int i = 0; i < mWaveNum; i++) {

            mPaths.get(i).reset();

            mPaths.get(i).moveTo(0, 0);

            for (float x = 0; x <= getWidth(); x += 20) {
                if (i % 2 == 0) {
                    y = (float) (A * Math.cos(ω * x + φ) + K + 20 * (i + 1));
                } else {
                    y = (float) ((A + (2 * (i + 1))) * Math.sin(ω * x + φ) + K);
                }
                mPaths.get(i).lineTo(x + mOffset, y);
            }

            mPaints.get(i).setShader(mShader1);
            mPaths.get(i).lineTo(getWidth(), 0);
            mPaths.get(i).close();
            canvas.drawPath(mPaths.get(i), mPaints.get(i));
        }

    }

    private void initAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, getWidth());
        valueAnimator.setDuration(1000);
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

}
