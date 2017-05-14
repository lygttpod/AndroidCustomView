package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Allen on 2017/5/14.
 * <p>
 * 自定义水平进度条
 */

public class HorizontalProgressBar extends View {

    private Paint bgPaint;
    private Paint progressPaint;

    private int width;
    private int height;
    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前进度
     */
    private float currentProgress;

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


    public HorizontalProgressBar(Context context) {
        super(context);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bgPaint = getPaint(10, Color.GRAY);
        progressPaint = getPaint(10, Color.RED);

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, bgPaint);

        canvas.drawLine(0, getHeight() / 2, currentProgress, getHeight() / 2, progressPaint);
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

                currentProgress = value * width / 100;
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }
                invalidate();

            }
        });
        progressAnimator.start();
    }


    public HorizontalProgressBar setProgress(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
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

    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    public HorizontalProgressBar setProgressListener(ProgressListener listener) {
        progressListener = listener;
        return this;
    }

}
