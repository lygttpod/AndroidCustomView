package com.allen.androidcustomview.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.allen.androidcustomview.R
import kotlin.math.min

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : yagang.li@yintech.cn
 *      date    : 2019/08/12
 *      desc    : 学习计划view
 * </pre>
 */
class StudyPlanProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mWidth = 0
    private var mHeight = 0

    private var iconPaint: Paint? = null
    private var linePaint: Paint = Paint()
    private var textPaint: Paint = Paint()
    private var iconRect = Rect()
    private var textRectF = Rect()

    private val ALL_POINT_SIZE = 7
    private var cellWidth = 0
    private var iconSize = 0
    private var textSize = 0
    private var textColor = 0
    private var progressWidth = 0
    private var uncheckedProgressColor = Color.GRAY
    private var checkedProgressColor = Color.YELLOW

    private var iconUncheckedBitmapRes: Bitmap? = null
    private var iconCheckedBitmapRes: Bitmap? = null

    private var dates: MutableList<ProgressData> = mutableListOf()

    private var marginLeftAndRight = 0

    init {
        marginLeftAndRight = dp2px(27f).toInt()
        iconSize = dp2px(14f).toInt()
        initAttr(context, attrs, defStyleAttr)
        initPaint()
    }

    private fun initAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StudyPlanProgressView)
        iconUncheckedBitmapRes = (typedArray.getDrawable(R.styleable.StudyPlanProgressView_sppv_iconUnchecked) as? BitmapDrawable)?.bitmap
        iconCheckedBitmapRes = (typedArray.getDrawable(R.styleable.StudyPlanProgressView_sppv_iconChecked) as? BitmapDrawable)?.bitmap
        iconSize = typedArray.getDimensionPixelOffset(R.styleable.StudyPlanProgressView_sppv_iconSize, dp2px(14f).toInt())
        progressWidth = typedArray.getDimensionPixelOffset(R.styleable.StudyPlanProgressView_sppv_progressWidth, dp2px(2f).toInt())
        uncheckedProgressColor = typedArray.getColor(R.styleable.StudyPlanProgressView_sppv_uncheckedProgressColor, uncheckedProgressColor)
        checkedProgressColor = typedArray.getColor(R.styleable.StudyPlanProgressView_sppv_checkedProgressColor, checkedProgressColor)
        textSize = typedArray.getDimensionPixelSize(R.styleable.StudyPlanProgressView_sppv_textSize, sp2px(11))
        textColor = typedArray.getColor(R.styleable.StudyPlanProgressView_sppv_textColor, checkedProgressColor)

        typedArray.recycle()
    }

    private fun initPaint() {
        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        iconPaint?.isFilterBitmap = true
        iconPaint?.isDither = true

        linePaint.strokeWidth = progressWidth.toFloat()
        linePaint.color = uncheckedProgressColor
        linePaint.isAntiAlias = true
        linePaint.style = Paint.Style.FILL_AND_STROKE

        textPaint.textSize = textSize.toFloat()
        textPaint.color = textColor
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        cellWidth = (mWidth - marginLeftAndRight * 2 - iconSize * ALL_POINT_SIZE) / (ALL_POINT_SIZE - 1)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawProgressLine(canvas)
        drawProgress(canvas)
    }

    private fun drawProgressLine(canvas: Canvas) {
        val lineStart = (marginLeftAndRight + iconSize / 2).toFloat()
        linePaint.color = uncheckedProgressColor
        canvas.drawLine(lineStart, (mHeight / 2).toFloat(), (mWidth - marginLeftAndRight - iconSize / 2).toFloat(), (mHeight / 2).toFloat(), linePaint)
        if (getDataSize() == 0) return
        val lineEnd = lineStart + (getDataSize() - 1) * (cellWidth + iconSize)
        linePaint.color = checkedProgressColor
        canvas.drawLine(lineStart, (mHeight / 2).toFloat(), lineEnd, (mHeight / 2).toFloat(), linePaint)
    }

    private fun drawProgress(canvas: Canvas) {
        if (iconUncheckedBitmapRes == null || iconCheckedBitmapRes == null) return
        for (i in 0 until ALL_POINT_SIZE) {
            val left = marginLeftAndRight + (cellWidth + iconSize) * i
            iconRect.set(left, (mHeight - iconSize) / 2, left + iconSize, (mHeight + iconSize) / 2)
            val bitmap = if (isFinished(i)) iconCheckedBitmapRes else iconUncheckedBitmapRes
            canvas.drawBitmap(bitmap, null, iconRect, iconPaint)
            drawText(canvas, i)
        }
    }

    private fun isFinished(position: Int): Boolean {
        return if (position <= getDataSize() - 1) {
            dates[position].isFinished
        } else false
    }

    private fun drawText(canvas: Canvas, position: Int) {
        if (getDataSize() == 0) return
        if (position < getDataSize()) {
            val textWidth = textPaint.measureText(dates[position].content).toInt()
            val isTop = position % 2 == 0
            val left = marginLeftAndRight + iconSize / 2 + (cellWidth + iconSize) * position - textWidth / 2
            textRectF.left = left
            textRectF.right = left + textWidth
            textRectF.top = if (isTop) 0 else (mHeight + iconSize) / 2
            textRectF.bottom = if (isTop) (mHeight - iconSize) / 2 else mHeight

            val fontMetrics = textPaint.fontMetricsInt
            val baseline = (textRectF.bottom + textRectF.top - fontMetrics.bottom - fontMetrics.top) / 2
            //文字绘制到整个布局的中心位置
            canvas.drawText(dates[position].content, textRectF.centerX().toFloat(), baseline.toFloat(), textPaint)
        }
    }

    fun setData(date: List<String>?) {
        dates.clear()
        val timeList = date?.toMutableList() ?: mutableListOf()
        timeList.forEach {
            dates.add(ProgressData(it, true))
        }
        invalidate()
    }

    private fun getDataSize() = min(dates.size, 7)

    fun dp2px(dpVal: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, resources.displayMetrics)
    }

    fun sp2px(spVal: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal.toFloat(), resources.displayMetrics).toInt()
    }

    class ProgressData(var content: String, var isFinished: Boolean)
}