package com.allen.androidcustomview.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.allen.androidcustomview.R

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2019/07/07
 *      desc    :
 * </pre>
 */
class ArcView : View {
    private var mWidth = 0
    private var mHeight = 0
    /**
     * 弧形高度
     */
    private var mArcHeight = 0
    /**
     * 背景颜色
     */
    private var mBgColor = Color.WHITE
    private var mPaint = Paint()
    private var mContext: Context? = null

    private var mPath = Path()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs, defStyleAttr)
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView)
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0)
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, Color.WHITE)
        typedArray.recycle()

        mContext = context
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = mBgColor
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 1f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        resetPath()
    }

    private fun resetPath() {
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(0f, (mHeight - mArcHeight).toFloat())
        mPath.quadTo((mWidth / 2).toFloat(), (mHeight + mArcHeight).toFloat(), mWidth.toFloat(), (mHeight - mArcHeight).toFloat())
        mPath.lineTo(mWidth.toFloat(), 0f)
        mPath.close()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaint)
    }

    fun setArcViewBgColor(color: Int) {
        mBgColor = color
        mPaint.color = color
        invalidate()
    }

    fun getArcViewBgColor() = mBgColor

    fun setArcViewHeight(height: Int) {
        if (height == mHeight) return
        mHeight = height
        resetPath()
        invalidate()
    }
}