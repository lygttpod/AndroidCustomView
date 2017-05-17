package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Allen on 2017/5/14.
 * <p>
 * 自定义圆形进度条
 */

public class CircleProgressBarView extends View {

    private float width;
    private float height;

    /**
     * 圆心x坐标
     */
    private float centerX;
    /**
     * 圆心y坐标
     */
    private float centerY;
    /**
     * 圆的半径
     */
    private float radius;

    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前进度
     */
    private float currentProgress;

    /**
     * 圆形进度条底色画笔
     */
    private Paint circleBgPaint;
    /**
     * 圆形进度条进度画笔
     */
    private Paint progressPaint;

    private int circleBgColor = Color.GRAY;
    private int progressColor = Color.RED;

    /**
     * 默认圆环的宽度
     */
    private int defaultStrokeWidth = 10;
    /**
     * 圆形背景画笔宽度
     */
    private int circleBgStrokeWidth = defaultStrokeWidth;
    /**
     * 圆形进度画笔宽度
     */
    private int progressStrokeWidth = defaultStrokeWidth;


    /**
     * 扇形所在矩形
     */
    private RectF rectF = new RectF();

    /**
     * 进度动画
     */
    private ValueAnimator progressAnimator;

    /**
     * 动画执行时间
     */
    private int duration = 2000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;

    private ProgressListener progressListener;

    public CircleProgressBarView(Context context) {
        this(context, null);
    }

    public CircleProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

    }

    private void initPaint() {
        circleBgPaint = getPaint(circleBgStrokeWidth, circleBgColor);

        progressPaint = getPaint(progressStrokeWidth, progressColor);

    }

    private Paint getPaint(int strokeWidth, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        return paint;
    }

    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new DecelerateInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();

                currentProgress = value * 360 / 100;

                if (progressListener != null) {
                    progressListener.currentProgressListener(roundTwo(value));
                }
                invalidate();

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        centerX = w / 2;
        centerY = h / 2;

        radius = Math.min(w, h) / 2 - circleBgStrokeWidth;

        rectF.set(centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, circleBgPaint);
        canvas.drawArc(rectF, 90, currentProgress, false, progressPaint);
    }

    public void startProgressAnimation() {
        progressAnimator.start();
    }

    public void pauseProgressAnimation() {
        progressAnimator.pause();
    }

    public void resumeProgressAnimation() {
        progressAnimator.resume();
    }

    public void stopProgressAnimation() {
        progressAnimator.end();
    }

    public CircleProgressBarView setProgress(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    public CircleProgressBarView setProgressListener(ProgressListener listener) {
        progressListener = listener;
        return this;
    }
    /**
     * 将一个小数四舍五入，保留两位小数返回
     * @param originNum
     * @return
     */
    public static float roundTwo(float originNum) {
        return (float) (Math.round(originNum * 10) / 10.0);
    }
}
