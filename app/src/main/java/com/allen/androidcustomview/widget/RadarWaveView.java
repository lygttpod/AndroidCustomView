package com.allen.androidcustomview.widget;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by Allen on 2017/4/10.
 * 雷达水波扩散动画
 */

public class RadarWaveView extends View {

    /**
     * 圆形背景画笔
     */
    private Paint mBgPaint;
    /**
     * 圆形背景颜色
     */
    private int mBgColor = 0x66FFFFFF;
    /**
     * 圆形背景的半径
     */
    private float mBgRadius;
    /**
     * 圆形背景最大半径比例
     */
    private float mMaxRadiusRate = 0.85f;

    /**
     * 扩散水波的颜色
     */
    private int mCircleEdgeColor_green = 0x2fffd1;
    private int mCircleEdgeColor_red = 0xFF4081;
    private int mCircleEdgeColor_yellow = 0xfeff38;

    private int[] colors = {mCircleEdgeColor_green, mCircleEdgeColor_yellow, mCircleEdgeColor_red};

    /**
     * 阴影颜色
     */
    private int mCircleShadowColor = 0x3a0909;
    /**
     * 扩散水波的画笔
     */
    private Paint mCircleWavePaint;
    /**
     * 扩散水波默认半径
     */
    private float mDefaultWaveRadius = 0;
    /**
     * 当前扩散水波的半径
     */
    private float mCurrentWaveRadius;


    /**
     * 扩散水波执行时间
     */
    private long mDuration = 2000;

    /**
     * 水波动画
     */
    private ValueAnimator mAnimator_green;
    private ValueAnimator mAnimator_yellow;
    private ValueAnimator mAnimator_red;

    private ValueAnimator[] valueAnimators = new ValueAnimator[3];
    /**
     * 动画集
     */
    private AnimatorSet mAnimatorSet;


    public RadarWaveView(Context context) {
        this(context, null);
    }

    public RadarWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBgPaint();
        initCirclePaint();

        for (int i = 0; i < valueAnimators.length; i++) {
            initAnimation(i);
        }
//        initAnimation_green();
//        initAnimation_yellow();
//        initAnimation_red();

        initAnimatorSet();


    }


    /**
     * 初始化背景画笔
     */
    private void initBgPaint() {
        mBgPaint = new Paint();
        mBgPaint.setStrokeWidth(5);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mBgColor);
    }

    /**
     * 初始化水波圆圈的画笔
     */
    private void initCirclePaint() {
        mCircleWavePaint = new Paint();
        mCircleWavePaint.setStrokeWidth(3);
        mCircleWavePaint.setStyle(Paint.Style.STROKE);
        mCircleWavePaint.setAntiAlias(true);
        mCircleWavePaint.setColor(mCircleEdgeColor_green);

        //设置圆环周围阴影效果  加上阴影必须取消硬件加速  没了硬件加速刷新UI导致卡卡的感觉 官方推荐阴影适合加在静态的图像上
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mCircleWavePaint.setShadowLayer(30, 0, 0, mCircleShadowColor);
    }


    private void initAnimation(final int animationP) {

        valueAnimators[animationP] = ValueAnimator.ofFloat(0, 1);
        valueAnimators[animationP].setDuration(mDuration);

        valueAnimators[animationP].setInterpolator(new DecelerateInterpolator());
        valueAnimators[animationP].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float v = (float) valueAnimator.getAnimatedValue();
                mCurrentWaveRadius = mDefaultWaveRadius + mBgRadius * v;

                mCircleWavePaint.setColor(colors[animationP]);
                if (v > 0.9) {
                    mCircleWavePaint.setAlpha((int) (((1 - v) + 0.4) * 100));
                } else {
                    mCircleWavePaint.setAlpha((int) ((v + 0.2) * 100));
                }

                mCircleWavePaint.setStrokeWidth(2 + 3 * v);
                invalidate();
            }
        });

    }


    /**
     * 初始化动画
     */
    private void initAnimation_green() {
        mAnimator_green = ValueAnimator.ofFloat(0, 1);
        mAnimator_green.setDuration(mDuration);

        mAnimator_green.setInterpolator(new DecelerateInterpolator());
        mAnimator_green.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float v = (float) valueAnimator.getAnimatedValue();
                mCurrentWaveRadius = mDefaultWaveRadius + mBgRadius * v;

                mCircleWavePaint.setColor(mCircleEdgeColor_green);
                if (v > 0.9) {
                    mCircleWavePaint.setAlpha((int) (((1 - v) + 0.4) * 100));
                } else {
                    mCircleWavePaint.setAlpha((int) ((v + 0.2) * 100));
                }

                mCircleWavePaint.setStrokeWidth(2 + 3 * v);
                invalidate();
            }
        });

    }

    /**
     * 初始化动画
     */
    private void initAnimation_yellow() {
        mAnimator_yellow = ValueAnimator.ofFloat(0, 1);
        mAnimator_yellow.setDuration(mDuration);

        //无线循环
//        mAnimator_yellow.setRepeatCount(3);

        mAnimator_yellow.setInterpolator(new DecelerateInterpolator());
        mAnimator_yellow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float v = (float) valueAnimator.getAnimatedValue();
                mCurrentWaveRadius = mDefaultWaveRadius + mBgRadius * v;
                mCircleWavePaint.setColor(mCircleEdgeColor_yellow);

                if (v > 0.9) {
                    mCircleWavePaint.setAlpha((int) (((1 - v) + 0.4) * 100));
                } else {
                    mCircleWavePaint.setAlpha((int) ((v + 0.2) * 100));
                }

                mCircleWavePaint.setStrokeWidth(2 + 3 * v);
                invalidate();
            }
        });

    }

    /**
     * 初始化动画
     */
    private void initAnimation_red() {
        mAnimator_red = ValueAnimator.ofFloat(0, 1);
        mAnimator_red.setDuration(mDuration);

        //无线循环
//        mAnimator_red.setRepeatCount(3);

        mAnimator_red.setInterpolator(new DecelerateInterpolator());
        mAnimator_red.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float v = (float) valueAnimator.getAnimatedValue();
                mCurrentWaveRadius = mDefaultWaveRadius + mBgRadius * v;

                mCircleWavePaint.setColor(mCircleEdgeColor_red);

                if (v > 0.9) {
                    mCircleWavePaint.setAlpha((int) (((1 - v) + 0.4) * 100));
                } else {
                    mCircleWavePaint.setAlpha((int) ((v + 0.2) * 100));
                }

                mCircleWavePaint.setStrokeWidth(2 + 3 * v);
                invalidate();
            }
        });

    }


    /**
     * 初始化动画集
     */
    private void initAnimatorSet() {
        mAnimatorSet = new AnimatorSet();

        mAnimatorSet.play(valueAnimators[1]).before(valueAnimators[2]).after(valueAnimators[0]);

        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBgRadius = Math.min(w, h) * mMaxRadiusRate / 2.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawWaveCircle(canvas);
    }

    /**
     * 画水波圆圈
     *
     * @param canvas 画布
     */
    private void drawWaveCircle(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCurrentWaveRadius, mCircleWavePaint);
    }

    /**
     * 画圆形的背景
     *
     * @param canvas 画笔
     */
    private void drawBg(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBgRadius, mBgPaint);
    }

    /**
     * 开启动画
     */
    public void start() {
        mAnimatorSet.start();
    }

    /**
     * 暂停动画
     */
    public void pause() {
        mAnimatorSet.pause();
    }

    /**
     * 停止动画
     */
    public void stop() {
        mAnimatorSet.end();
    }

}
