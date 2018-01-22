package com.allen.androidcustomview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.allen.androidcustomview.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by Allen on 2017/5/7.
 * 自定义支付密码输入框
 */

public class PayPsdInputView extends EditText {

    private Context mContext;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;


    private float cX;


    /**
     * 实心圆的半径
     */
    private int radius = 10;
    /**
     * view的高度
     */
    private int height;
    private int width;

    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    private int bottomLineLength;
    /**
     * 最大输入位数
     */
    private int maxCount = 6;
    /**
     * 圆的颜色   默认BLACK
     */
    private int circleColor = Color.BLACK;
    /**
     * 底部线的颜色   默认GRAY
     */
    private int bottomLineColor = Color.GRAY;

    /**
     * 分割线的颜色
     */
    private int borderColor = Color.GRAY;
    /**
     * 分割线的画笔
     */
    private Paint borderPaint;
    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;
    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = Color.GRAY;
    private int focusedColor = Color.BLUE;
    private RectF rectF = new RectF();
    private RectF focusedRecF = new RectF();
    private int psdType = 0;
    private final static int psdType_weChat = 0;
    private final static int psdType_bottomLine = 1;

    /**
     * 矩形边框的圆角
     */
    private int rectAngle = 0;
    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
    private Paint circlePaint;
    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;

    /**
     * 需要对比的密码  一般为上次输入的
     */
    private String mComparePassword = null;


    /**
     * 当前输入的位置索引
     */
    private int position = 0;

    private onPasswordListener mListener;

    public PayPsdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PayPsdInputView);
        maxCount = typedArray.getInt(R.styleable.PayPsdInputView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.PayPsdInputView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.PayPsdInputView_bottomLineColor, bottomLineColor);
        radius = typedArray.getDimensionPixelOffset(R.styleable.PayPsdInputView_radius, radius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.PayPsdInputView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.PayPsdInputView_divideLineColor, divideLineColor);
        psdType = typedArray.getInt(R.styleable.PayPsdInputView_psdType, psdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.PayPsdInputView_rectAngle, rectAngle);
        focusedColor = typedArray.getColor(R.styleable.PayPsdInputView_focusedColor, focusedColor);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        bottomLinePaint = getPaint(2, Paint.Style.FILL, bottomLineColor);

        borderPaint = getPaint(3, Paint.Style.STROKE, borderColor);

        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);

    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2;
        startY = h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的画会默认绘制输入的文字
//       super.onDraw(canvas);

        switch (psdType) {
            case psdType_weChat:
                drawWeChatBorder(canvas);
                drawItemFocused(canvas, position);
                break;
            case psdType_bottomLine:
                drawBottomBorder(canvas);
                break;
        }

        drawPsdCircle(canvas);
    }

    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }

    }

    private void drawItemFocused(Canvas canvas, int position) {
        if (position > maxCount - 1) {
            return;
        }
        focusedRecF.set(position * divideLineWStartX, 0, (position + 1) * divideLineWStartX,
                height);
        canvas.drawRoundRect(focusedRecF, rectAngle, rectAngle, getPaint(3, Paint.Style.STROKE, focusedColor));
    }

    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {

        for (int i = 0; i < maxCount; i++) {
            cX = startX + i * 2 * startX;
            canvas.drawLine(cX - bottomLineLength / 2,
                    height,
                    cX + bottomLineLength / 2,
                    height, bottomLinePaint);
        }
    }

    /**
     * 画密码实心圆
     *
     * @param canvas
     */
    private void drawPsdCircle(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    radius,
                    circlePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.position = start + lengthAfter;
        textLength = text.toString().length();

        if (textLength == maxCount) {
            if (mListener != null) {
                if (TextUtils.isEmpty(mComparePassword)) {
                    mListener.inputFinished(getPasswordString());
                } else {
                    if (TextUtils.equals(mComparePassword, getPasswordString())) {
                        mListener.onEqual(getPasswordString());
                    } else {
                        mListener.onDifference(mComparePassword, getPasswordString());
                    }
                }
            }
        }

        invalidate();

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getPasswordString() {
        return getText().toString().trim();
    }

    public void setComparePassword(String comparePassword, onPasswordListener listener) {
        mComparePassword = comparePassword;
        mListener = listener;
    }

    public void setComparePassword(onPasswordListener listener) {
        mListener = listener;
    }

    public void setComparePassword(String psd) {
        mComparePassword = psd;
    }

    /**
     * 清空密码
     */
    public void cleanPsd() {
        setText("");
    }

    /**
     * 密码比较监听
     */
    public interface onPasswordListener {
        void onDifference(String oldPsd, String newPsd);

        void onEqual(String psd);

        void inputFinished(String inputPsd);
    }
}
