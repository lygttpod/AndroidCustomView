package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by allen on 2017/6/5.
 * <p>
 * 字符串逐字显示的view
 */

public class FadeInTextView extends TextView {


    private Rect textRect = new Rect();

    private StringBuffer stringBuffer = new StringBuffer();

    private String[] arr;

    private int textCount;

    private int currentIndex = -1;

    /**
     * 每个字出现的时间
     */
    private int duration = 300;
    private ValueAnimator textAnimation;

    private TextAnimationListener textAnimationListener;


    public FadeInTextView setTextAnimationListener(TextAnimationListener textAnimationListener) {
        this.textAnimationListener = textAnimationListener;
        return this;
    }

    public FadeInTextView(Context context) {
        this(context, null);
    }

    public FadeInTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (stringBuffer != null) {
            drawText(canvas, stringBuffer.toString());
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, String textString) {
        textRect.left = getPaddingLeft();
        textRect.top = getPaddingTop();
        textRect.right = getWidth() - getPaddingRight();
        textRect.bottom = getHeight() - getPaddingBottom();
        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, getPaddingLeft(), baseline, getPaint());
    }

    /**
     * 文字逐个显示动画  通过插值的方式改变数据源
     */
    private void initAnimation() {

        textAnimation = ValueAnimator.ofInt(0, textCount - 1);
        textAnimation.setDuration(textCount * duration);
        textAnimation.setInterpolator(new LinearInterpolator());
        textAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int index = (int) valueAnimator.getAnimatedValue();
                //过滤去重，保证每个字只重绘一次
                if (currentIndex != index) {
                    stringBuffer.append(arr[index]);
                    Log.d("allen1", "onAnimationUpdate: " + stringBuffer.toString());

                    currentIndex = index;
                    if (currentIndex == (textCount - 1)) {
                        if (textAnimationListener != null) {
                            currentIndex = -1;
                            textAnimationListener.animationFinish();
                        }
                    }

                    invalidate();
                }
            }
        });
    }

    /**
     * 设置逐渐显示的字符串
     *
     * @param textString
     * @return
     */
    public FadeInTextView setTextString(String textString) {
        if (textString != null) {
            textCount = textString.length();
            arr = new String[textCount];
            for (int i = 0; i < textCount; i++) {
                arr[i] = textString.substring(i, i + 1);
            }
            initAnimation();
        }

        return this;
    }

    /**
     * 开启动画
     *
     * @return
     */
    public FadeInTextView startFadeInAnimation() {
        if (textAnimation != null) {
            stringBuffer.setLength(0);
            currentIndex = -1;
            textAnimation.start();
        }
        return this;
    }

    /**
     * 停止动画
     *
     * @return
     */
    public FadeInTextView stopFadeInAnimation() {
        if (textAnimation != null) {
            textAnimation.end();
        }
        return this;
    }

    /**
     * 回调接口
     */
    public interface TextAnimationListener {
        void animationFinish();
    }
}
