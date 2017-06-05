package com.allen.androidcustomview.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;

/**
 * Created by allen on 2017/5/23.
 * <p>
 * loading按钮
 */

public class LoadingButton extends Button {

    public int mWidth;
    public int mHeight;
    private RectF textRect = new RectF();

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint circlePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint circlePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint circlePaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int circleColor = 0xffffff;
    //    private int circleColor = 0xf66b12;
    private int duration = 1000;

    private float centerX;
    private float centerY;

    private float radius;

    private AnimatorSet animatorSet;
    private ValueAnimator animator1;
    private ValueAnimator animator2;
    private ValueAnimator animator3;


    private boolean isLoading = false;

    public LoadingButton(Context context) {
        this(context, null);
    }

    public LoadingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        animatorSet = new AnimatorSet();
        circleAnimator1();
        circleAnimator2();
        circleAnimator3();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.play(animator1).with(animator2).with(animator3);

    }

    private void circleAnimator1() {
        animator1 = ValueAnimator.ofInt(255, 76, 165);
        animator1.setDuration(duration);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circlePaint1.setAlpha((Integer) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    private void circleAnimator2() {
        animator2 = ValueAnimator.ofInt(165, 255, 76);
        animator2.setDuration(duration);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circlePaint2.setAlpha((Integer) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    private void circleAnimator3() {
        animator3 = ValueAnimator.ofInt(76, 165, 255);
        animator3.setDuration(duration);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circlePaint3.setAlpha((Integer) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    private void initPaint() {
        initTextPaint();

        circlePaint1 = getPaint(dp2px(1), circleColor, Paint.Style.FILL);
        circlePaint1.setAlpha(255);
        circlePaint2 = getPaint(dp2px(1), circleColor, Paint.Style.FILL);
        circlePaint2.setAlpha(165);
        circlePaint3 = getPaint(dp2px(1), circleColor, Paint.Style.FILL);
        circlePaint3.setAlpha(76);
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(sp2px(16));
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = width;
        mHeight = height;

        centerX = mWidth / 2;
        centerY = mHeight / 2;

        radius = mHeight / 8;
        Log.d("allen", "onMeasure: "+centerX);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (isLoading) {
            drawLoading(canvas);
        } else {
            super.onDraw(canvas);
        }

    }

    private void drawLoading(Canvas canvas) {
        canvas.drawCircle(centerX - radius * 4, centerY, radius, circlePaint1);

        canvas.drawCircle(centerX, centerY, radius, circlePaint2);

        canvas.drawCircle(centerX + radius * 4, centerY, radius, circlePaint3);
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, String textString) {
        textRect.set(0, 0, mWidth, mHeight);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, textRect.centerX(), baseline, textPaint);
    }


    public void startLoading() {
        if (animatorSet != null) {
            isLoading = true;
            animatorSet.start();
        }
    }

    public void stopLoading() {
        if (animatorSet != null) {
            isLoading = false;
            animatorSet.end();
            postInvalidate();
        }
    }

    /**
     * 统一处理paint
     *
     * @param strokeWidth
     * @param color
     * @param style
     * @return
     */
    public Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
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

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

}
