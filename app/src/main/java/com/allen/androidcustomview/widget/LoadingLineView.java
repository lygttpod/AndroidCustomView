package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Allen on 2018/1/31.
 * <p>
 * 线性加载动画
 */

public class LoadingLineView extends View {

    private int mWidth;
    private int mHeight;

    /**
     * 动画起点x坐标
     */
    private int centerX;
    /**
     * 动画起点y坐标
     */
    private int centerY;

    /**
     * 偏移距离
     */
    private float dis;

    /**
     * view真实高度
     */
    private int mViewHeight;

    /**
     * 背景色画笔
     */
    private Paint bgPaint;
    /**
     * loading画笔
     */
    private Paint loadingPaint;

    /**
     * 画笔宽度（等于vie高度）
     */
    private int paintWidth;

    /**
     * 底色
     */
    private int bgColor = 0xFFe1e5e8;

    /**
     * loading颜色
     */
    private int loadingColor = 0xFFf66b12;


    /**
     * 动画
     */
    private ValueAnimator loadingAnimator;
    /**
     * 动画执行时间
     */
    private int duration = 800;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 0;

    /**
     * 是否停止动画（恢复初始状态）
     */
    private boolean isStopAnimation = false;

    public LoadingLineView(Context context) {
        this(context, null);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAtt(attrs);
        init();
    }

    /**
     * 想实现颜色等参数可配置，在这里实现配置即可,笔者就不多写了
     *
     * @param attrs attrs
     */
    private void getAtt(AttributeSet attrs) {

    }

    private void init() {
        paintWidth = dp2px(2);
        mViewHeight = paintWidth;
        bgPaint = getPaint(paintWidth, bgColor, Paint.Style.FILL);
        loadingPaint = getPaint(paintWidth, loadingColor, Paint.Style.FILL);
    }


    /**
     * 统一处理paint
     *
     * @param strokeWidth 画笔宽度
     * @param color       颜色
     * @param style       风格
     * @return paint
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        centerX = w / 2;
        centerY = h / 2;

        initLoadingAnimation();
        startLoading();
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
                mWidth = width;
                break;
        }
        return mWidth;
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
                mHeight = mViewHeight;
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画底色
         */
        canvas.drawLine(0, centerY, mWidth, centerY, bgPaint);

        if (!isStopAnimation) {
            /**
             * loading向左扩散
             */
            canvas.drawLine(centerX, centerY, centerX - dis, centerY, loadingPaint);
            /**
             * loading向右扩散
             */
            canvas.drawLine(centerX, centerY, centerX + dis, centerY, loadingPaint);
        }

    }

    /**
     * 初始化动画
     */
    private void initLoadingAnimation() {
        final float loadingMoveDistance = mWidth / 2;
        loadingAnimator = ValueAnimator.ofFloat(0, loadingMoveDistance);
        loadingAnimator.setDuration(duration);
        loadingAnimator.setStartDelay(startDelay);
        loadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        loadingAnimator.setInterpolator(new LinearInterpolator());
        loadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                dis = value;
                if (value <= loadingMoveDistance / 2) {
                    loadingPaint.setAlpha((int) ((255 * value) * 2 / loadingMoveDistance));
                } else {
                    loadingPaint.setAlpha((int) (255 - (255 * value) * 2 / loadingMoveDistance));
                }
                invalidate();
            }
        });
    }

    /**
     * 开启动画
     */
    public void startLoading() {
        if (loadingAnimator != null) {
            loadingAnimator.start();
        }
        isStopAnimation = false;
    }

    /**
     * 结束动画
     */
    public void stopLoading() {
        if (loadingAnimator != null) {
            loadingAnimator.cancel();
        }
        isStopAnimation = true;
        invalidate();
    }


    /**
     * dp 2 px
     *
     * @param dpVal dp
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
