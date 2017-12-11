package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;

/**
 * Created by Allen on 2017/11/30.
 * <p>
 * 产品购买进度条
 */

public class ProductProgressBar extends View {


    private Paint bgPaint;
    private Paint progressPaint;

    private Paint textPaint;

    private int mWidth;
    private int mHeight;

    private int mViewHeight;
    /**
     * 进度
     */
    private float mProgress;
    //描述文字的高度
    private float textHeight;
    //描述文字的高度
    private float textWidth;

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
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;

    /**
     * 进度条画笔的宽度
     */
    private int progressPaintWidth;

    private int progressHeight;

    /**
     * 进度条距离提示框的高度
     */
    private int progressMarginTop;

    /**
     * 进度移动的距离
     */
    private float moveDis;

    private Rect textRect = new Rect();

    private String textString = "已售0%";
    /**
     * 百分比文字字体大小
     */
    private int textPaintSize;

    /**
     * 进度条背景颜色
     */
    private int bgColor = 0xFFeaeef0;
    /**
     * 进度条颜色
     */
    private int progressColor = 0xFFf66b12;


    private RectF bgRectF = new RectF();
    private RectF progressRectF = new RectF();

    /**
     * 圆角矩形的圆角半径
     */
    private int roundRectRadius;

    /**
     * 进度监听回调
     */
    private ProgressListener progressListener;

    public ProductProgressBar(Context context) {
        this(context, null);
    }

    public ProductProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initPaint();
        initTextPaint();
    }

    /**
     * 初始化画笔宽度及view大小
     */
    private void init() {
        progressPaintWidth = dp2px(1);
        progressHeight = dp2px(3);
        roundRectRadius = dp2px(3);
        textPaintSize = sp2px(10);
        textHeight = dp2px(10);
        progressMarginTop = dp2px(4);

        //view真实的高度
        mViewHeight = (int) (textHeight + progressMarginTop + progressPaintWidth * 2 + progressHeight);
    }


    private void initPaint() {
        bgPaint = getPaint(progressPaintWidth, bgColor, Paint.Style.FILL);
        progressPaint = getPaint(progressPaintWidth, progressColor, Paint.Style.FILL);
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textPaintSize);
        textPaint.setColor(progressColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
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

        //绘制文字
        drawText(canvas, textString);
        //背景
        drawBgProgress(canvas);
        //进度条
        drawProgress(canvas);
    }

    private void drawBgProgress(Canvas canvas) {
        bgRectF.left = 0;
        bgRectF.top = textHeight + progressMarginTop;
        bgRectF.right = this.getMeasuredWidth();
        bgRectF.bottom = bgRectF.top + progressHeight;
        canvas.drawRoundRect(bgRectF, roundRectRadius, roundRectRadius, bgPaint);
    }

    private void drawProgress(Canvas canvas) {
        progressRectF.left = 0;
        progressRectF.top = textHeight + progressMarginTop;
        progressRectF.right = currentProgress;
        progressRectF.bottom = progressRectF.top + progressHeight;
        canvas.drawRoundRect(progressRectF, roundRectRadius, roundRectRadius, progressPaint);
    }


    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, String textString) {
        textRect.left = (int) moveDis;
        textRect.top = 0;
        textRect.right = (int) (textPaint.measureText(textString) + moveDis);
        textRect.bottom = (int) textHeight;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, textRect.centerX(), baseline, textPaint);
    }


    /**
     * 进度移动动画  通过插值的方式改变移动的距离
     */
    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                textString = "已售" + formatNum((int) value) + "%";
                textWidth = textPaint.measureText(textString);
                currentProgress = value * mWidth / 100;
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }
                //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
                if (currentProgress >= textWidth && currentProgress <= mWidth) {
                    moveDis = currentProgress - textWidth;
                }
                invalidate();
            }
        });
        if (!progressAnimator.isStarted()) {
            progressAnimator.start();
        }
    }

    /**
     * 回调接口
     */
    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    /**
     * 回调监听事件
     *
     * @param listener
     * @return
     */
    public ProductProgressBar setProgressListener(ProgressListener listener) {
        progressListener = listener;
        return this;
    }

    public ProductProgressBar setProgress(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    /**
     * 格式化数字(保留一位小数)
     *
     * @param money
     * @return
     */
    public static String formatNum(int money) {
        DecimalFormat format = new DecimalFormat("0");
        return format.format(money);
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
