package com.allen.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by allen on 2017/6/5.
 * <p>
 * 字符串逐字显示的view
 *   fadeInTextView
 *               .setTextString("自己的字符串")
 *               .startFadeInAnimation()
 *               .setTextAnimationListener(new FadeInTextView.TextAnimationListener() {
 *                   @Override
 *                   public void animationFinish() {
 *
 *                   }
 *               });
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
//        使用setText代替重绘就不用自己去绘制text了
//        if (stringBuffer != null) {
//            drawText(canvas, stringBuffer.toString());
//        }
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

        //从0到textCount - 1  是设置从第一个字到最后一个字的变化因子
        textAnimation = ValueAnimator.ofInt(0, textCount - 1);
        //执行总时间就是每个字的时间乘以字数
        textAnimation.setDuration(textCount * duration);
        //匀速显示文字
        textAnimation.setInterpolator(new LinearInterpolator());
        textAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int index = (int) valueAnimator.getAnimatedValue();
                //过滤去重，保证每个字只重绘一次
                if (currentIndex != index) {
                    stringBuffer.append(arr[index]);
                    currentIndex = index;
                    //所有文字都显示完成之后进度回调结束动画
                    if (currentIndex == (textCount - 1)) {
                        if (textAnimationListener != null) {
                            textAnimationListener.animationFinish();
                        }
                    }

                    //新思路的做法
                    setText(stringBuffer.toString());

                    /**
                     * 之前的做法刷新重绘text,需要自己控制文字的绘制，
                     * 看到网友的评论开拓了思路，既然是直接集成TextView
                     * 就可以直接使用setText()方法进行设置值了
                     */
                    //invalidate();老思路的做法
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
            //总字数
            textCount = textString.length();
            //存放单个字的数组
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
