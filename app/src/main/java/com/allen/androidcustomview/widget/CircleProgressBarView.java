package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.allen.androidcustomview.R;

/**
 * Created by Allen on 2017/5/14.
 * <p>
 * 自定义圆形进度条
 */

public class CircleProgressBarView extends View {

    private Context mContext;


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

    /**
     * 进度条背景颜色
     */
    private int circleBgColor = 0xFFe1e5e8;
    /**
     * 进度条颜色
     */
    private int progressColor = 0xFFf66b12;

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
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;

    private boolean isDrawCenterProgressText;
    private int centerProgressTextSize = 10;
    private int centerProgressTextColor = Color.BLACK;

    private Paint centerProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ProgressListener progressListener;

    public CircleProgressBarView(Context context) {
        this(context, null);
    }

    public CircleProgressBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttr(attrs);
        initPaint();
        initTextPaint();
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBarView);

        circleBgStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBarView_circleBgStrokeWidth, defaultStrokeWidth);
        progressStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBarView_progressStrokeWidth, defaultStrokeWidth);

        circleBgColor = typedArray.getColor(R.styleable.CircleProgressBarView_circleBgColor, circleBgColor);
        progressColor = typedArray.getColor(R.styleable.CircleProgressBarView_progressColor, progressColor);

        duration = typedArray.getColor(R.styleable.CircleProgressBarView_circleAnimationDuration, duration);

        isDrawCenterProgressText = typedArray.getBoolean(R.styleable.CircleProgressBarView_isDrawCenterProgressText, false);

        centerProgressTextColor = typedArray.getColor(R.styleable.CircleProgressBarView_centerProgressTextColor, centerProgressTextColor);
        centerProgressTextSize = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBarView_centerProgressTextSize, sp2px(centerProgressTextSize));

        typedArray.recycle();
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

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        centerProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerProgressTextPaint.setTextSize(centerProgressTextSize);
        centerProgressTextPaint.setColor(centerProgressTextColor);
        centerProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        centerProgressTextPaint.setAntiAlias(true);
    }

    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mProgress = value;
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

        centerX = w / 2;
        centerY = h / 2;

        radius = Math.min(w, h) / 2 - Math.max(circleBgStrokeWidth, progressStrokeWidth);

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
        if (isDrawCenterProgressText) {
            drawCenterProgressText(canvas, (int) mProgress + "%");
        }
    }

    private void drawCenterProgressText(Canvas canvas, String currentProgress) {
        Paint.FontMetricsInt fontMetrics = centerProgressTextPaint.getFontMetricsInt();
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        //文字绘制到整个布局的中心位置
        canvas.drawText(currentProgress, rectF.centerX(), baseline, centerProgressTextPaint);
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


    /**
     * 传入一个进度值，从0到progress动画变化
     *
     * @param progress
     * @return
     */
    public CircleProgressBarView setProgressWithAnimation(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    /**
     * 实时进度，适用于下载进度回调时候之类的场景
     *
     * @param progress
     * @return
     */
    public CircleProgressBarView setCurrentProgress(float progress) {
        mProgress = progress;
        currentProgress = progress * 360 / 100;
        invalidate();
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
     *
     * @param originNum
     * @return
     */
    public static float roundTwo(float originNum) {
        return (float) (Math.round(originNum * 10) / 10.00);
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
