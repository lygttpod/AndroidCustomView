package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.allen.androidcustomview.R;

import java.text.DecimalFormat;

/**
 * Created by Allen on 2017/5/14.
 * <p>
 * 自定义水平进度条
 */

public class HorizontalProgressBar extends View {


    private Paint bgPaint;
    private Paint progressPaint;

    private Paint tipPaint;
    private Paint textPaint;

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
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;


    private ProgressListener progressListener;


    private Path path = new Path();

    private int strokeWidth = 5;

    private int tipHeight = 50;
    private int tipWidth = 100;

    private int dis = 10;


    private float move;

    private Rect textRect = new Rect();
    private String textString;

    private int bgColor = 0xFFe1e5e8;

    private int progressColor = 0xFFf66b12;

    private RectF rectF = new RectF();
    public HorizontalProgressBar(Context context) {
        super(context);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bgPaint = getPaint(strokeWidth, bgColor);
        progressPaint = getPaint(strokeWidth, progressColor);

        tipPaint = getPaint(1, progressColor);
        tipPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //设置一个默认值，就是这个View的默认宽度为500，这个看我们自定义View的要求
        int result = 300;
        if (specMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = specSize;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 100;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
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

//        canvas.drawLine(getPaddingLeft(), getHeight()-strokeWidth , getWidth(), getHeight()-strokeWidth , bgPaint);
//
//        canvas.drawLine(getPaddingLeft(), getHeight()-strokeWidth , currentProgress, getHeight()-strokeWidth , progressPaint);

        canvas.drawLine(getPaddingLeft(), tipHeight +20 , getWidth(), tipHeight +20 , bgPaint);

        canvas.drawLine(getPaddingLeft(), tipHeight +20 , currentProgress, tipHeight +20 , progressPaint);

//        drawTipPath(canvas,move);
        drawTipView(canvas);

        drawText(canvas,textString);

    }

    private void drawTipPath(Canvas canvas, int move) {
        path.moveTo(0+move,0);
        path.lineTo(0+move, tipHeight);
        path.lineTo((tipWidth -dis)/2+move, tipHeight);
        path.lineTo((tipWidth -dis)/2+dis/2+move, tipHeight +5);
        path.lineTo((tipWidth -dis)/2+dis+move, tipHeight);
        path.lineTo(tipWidth +move, tipHeight);
        path.lineTo(tipWidth +move,0);
        path.close();

        drawTipView(canvas);


        path.reset();
    }

    private void drawTipView(Canvas canvas) {
        drawRoundRect(canvas);

        drawTriangle(canvas);
    }


    private void drawRoundRect(Canvas canvas) {

        rectF.set(move,0, tipWidth +move, tipHeight);
        canvas.drawRoundRect(rectF,5,5,tipPaint);
        canvas.drawPath(path,tipPaint);

    }

    private void drawTriangle(Canvas canvas) {
        path.moveTo(tipWidth /2-dis+move, tipHeight);
        path.lineTo(tipWidth /2+move, tipHeight +dis);
        path.lineTo(tipWidth /2+dis+move, tipHeight);
        canvas.drawPath(path,tipPaint);
        path.reset();

    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, String textString) {
        textRect.left = (int) move;
        textRect.top = 0;
        textRect.right = (int) (tipWidth +move);
        textRect.bottom = tipHeight;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString+"%", textRect.centerX(), baseline, textPaint);
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
                textString = formatNum(value);
                currentProgress = value * width / 100;
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }
                if (currentProgress>=(tipWidth /2)&&currentProgress<=(width- tipWidth /2)){
                    move = currentProgress- tipWidth /2;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseProgressAnimation() {
        progressAnimator.pause();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
    /**
     * 格式化数字(保留两位小数)
     * @param money
     * @return
     */
    public static String formatNumTwo(double money) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money);
    }

    /**
     * 格式化数字(保留两位小数)
     * @param money
     * @return
     */
    public static String formatNum(float money) {
        DecimalFormat format = new DecimalFormat("0");
        return format.format(money);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

}
