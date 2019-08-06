package com.allen.androidcustomview.widget.vote

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.allen.androidcustomview.R
import kotlin.math.max

/**
 * <pre>
 *      @author : Allen
 *      date    : 2019/07/30
 *      desc    :
 * </pre>
 */
class VoteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0

    private var bgRectF = RectF()
    private var progressRectF = RectF()
    private var voteContentRectF = Rect()
    private var voteResultRectF = Rect()
    private var voteRightIconRectF = Rect()

    private var voteResultBaseline = 0
    private var voteContentBaseline = 0

    private var progressPaint: Paint? = null
    private var iconPaint: Paint? = null
    private var bgPaint: Paint? = null
    private var borderPaint: Paint? = null

    private var voteContentTextPaint: Paint? = null
    private var voteResultTextPaint: Paint? = null

    private var animDuration = 1000L

    private var mScale = 1f

    private var mProgress = -1f
    private var mVoteContent: String? = null
    private var mVoteResult: String? = null

    private var valueAnimator: ValueAnimator? = null

    private var textMarginLeft = 0
    private var voteResultMarginRight = 0

    private var textPaintSize: Int = 0

    private var rightCheckedBitmapRes: Bitmap? = null

    private var rightIconWidth = 0
    private var rightIconHeight = 0

    private var checkedProgressColor = 0
    private var unCheckedProgressColor = 0

    private var checkedContentTextColor = 0
    private var uncheckedContentTextColor = 0

    private var checkedResultTextColor = 0
    private var uncheckedResultTextColor = 0

    private var borderColor = 0
    private var borderRadius = 0f

    private var isVoteChecked = false
    private var textWidth = 0

    private val defaultCheckedProgressColor = Color.argb(1, 255, 124, 5)
    private val defaultUncheckedProgressColor = Color.parseColor("#F3F3F3")
    private val defaultCheckedTextColor = Color.parseColor("#FF7C05")
    private val defaultUncheckedTextColor = Color.parseColor("#1a1a1a")
    private val defaultBorderColor = Color.parseColor("#e6e6e6")

    init {

        textMarginLeft = dp2px(15f).toInt()
        voteResultMarginRight = dp2px(15f).toInt()

        initAttr(context, attrs, defStyleAttr)

        initPaint()

        initVoteRightIcon()

        initColor()
    }

    private fun initColor() {
        voteContentTextPaint?.color = if (isVoteChecked) checkedContentTextColor else uncheckedContentTextColor
        voteResultTextPaint?.color = if (isVoteChecked) checkedResultTextColor else uncheckedResultTextColor
        progressPaint?.color = if (isVoteChecked) checkedProgressColor else unCheckedProgressColor
        bgPaint?.color = if (isVoteChecked) checkedProgressColor else unCheckedProgressColor
    }

    private fun initVoteRightIcon() {
        if (rightCheckedBitmapRes != null) {
            if (rightIconWidth == 0 || rightIconHeight == 0) {
                rightIconWidth = dp2px(36f).toInt()
                rightIconHeight = dp2px(36f).toInt()
            }
        }
    }

    private fun initAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoteView)

        checkedProgressColor = typedArray.getColor(R.styleable.VoteView_voteCheckedProgressColor, defaultCheckedProgressColor)
        unCheckedProgressColor = typedArray.getColor(R.styleable.VoteView_voteUncheckedProgressColor, defaultUncheckedProgressColor)

        checkedContentTextColor = typedArray.getColor(R.styleable.VoteView_voteCheckedContentTextColor, defaultCheckedTextColor)
        uncheckedContentTextColor = typedArray.getColor(R.styleable.VoteView_voteUncheckedContentTextColor, defaultUncheckedTextColor)

        checkedResultTextColor = typedArray.getColor(R.styleable.VoteView_voteCheckedResultTextColor, defaultCheckedTextColor)
        uncheckedResultTextColor = typedArray.getColor(R.styleable.VoteView_voteUncheckedResultTextColor, defaultUncheckedTextColor)

        textPaintSize = typedArray.getDimensionPixelSize(R.styleable.VoteView_voteTextSize, sp2px(15))

        borderColor = typedArray.getColor(R.styleable.VoteView_voteBorderColor, defaultBorderColor)
        borderRadius = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteBorderRadius, dp2px(1f).toInt()).toFloat()

        animDuration = typedArray.getInt(R.styleable.VoteView_voteAnimDuration, 500).toLong()

        rightCheckedBitmapRes = (typedArray.getDrawable(R.styleable.VoteView_voteCheckedIcon) as? BitmapDrawable)?.bitmap

        rightIconWidth = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteRightIconWidth, 0)
        rightIconHeight = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteRightIconHeight, 0)

        typedArray.recycle()
    }

    private fun initPaint() {
        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        iconPaint?.isFilterBitmap = true
        iconPaint?.isDither = true

        bgPaint = getPaint(dp2px(0.5f), Color.WHITE, Paint.Style.FILL)
        progressPaint = getPaint(dp2px(0.5f), unCheckedProgressColor, Paint.Style.FILL)
        borderPaint = getPaint(dp2px(0.5f), borderColor, Paint.Style.STROKE)

        voteContentTextPaint = getTextPaint(uncheckedContentTextColor, textPaintSize.toFloat())
        voteResultTextPaint = getTextPaint(uncheckedResultTextColor, textPaintSize.toFloat())
    }

    private fun getPaint(strokeWidth: Float, color: Int, style: Paint.Style): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = strokeWidth
        paint.color = color
        paint.isAntiAlias = true
        paint.style = style
        return paint
    }

    private fun getTextPaint(color: Int, textSize: Float): Paint {
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = textSize
        textPaint.color = color
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true
//        textPaint.typeface = Typeface.DEFAULT_BOLD
        return textPaint
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        setBgRect()
        setProgressRect()

        setVoteResultRect()
        setVoteContentRect()
        setVoteRightIconRect()

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBg(canvas)
        drawProgress(canvas)
        drawBorder(canvas)

        drawVoteContentText(canvas)
        drawVoteResultText(canvas)
        drawVoteRightIcon(canvas)
    }


    private fun setBgRect() {
        bgRectF.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
    }

    private fun setProgressRect() {
        progressRectF.set(0f, 0f, 0f, mHeight.toFloat())
    }

    private fun setVoteResultRect() {
        if (mVoteResult.isNullOrBlank()) return
        voteResultTextPaint!!.getTextBounds(mVoteResult, 0, mVoteResult!!.length, voteResultRectF)

        voteResultRectF.top = 0
        voteResultRectF.bottom = mHeight

        val fontMetrics = voteResultTextPaint!!.fontMetricsInt
        voteResultBaseline = (voteResultRectF.bottom + voteResultRectF.top - fontMetrics.bottom - fontMetrics.top) / 2
    }

    private fun setVoteContentRect() {
        if (mVoteContent.isNullOrBlank()) return
        voteContentTextPaint!!.getTextBounds(mVoteContent, 0, mVoteContent!!.length, voteContentRectF)

        textWidth = (voteContentRectF.right - voteContentRectF.left)
        voteContentRectF.top = 0
        voteContentRectF.bottom = mHeight
        voteContentRectF.left = (mWidth - textWidth) / 2
        voteContentRectF.right = voteContentRectF.left + textWidth

        val fontMetrics = voteContentTextPaint!!.fontMetricsInt
        voteContentBaseline = (voteContentRectF.bottom + voteContentRectF.top - fontMetrics.bottom - fontMetrics.top) / 2
    }

    private fun setVoteRightIconRect() {
        voteRightIconRectF.set(voteContentRectF.right + voteResultMarginRight, (mHeight - rightIconHeight) / 2, voteContentRectF.right + voteResultMarginRight + rightIconWidth, (mHeight + rightIconHeight) / 2)
    }


    private fun drawBg(canvas: Canvas) {
        if (mProgress != -1f) {
            bgPaint?.color = Color.WHITE
        }
        canvas.drawRoundRect(bgRectF, 0f, 0f, bgPaint!!)
    }

    private fun drawProgress(canvas: Canvas) {
        if (mProgress == -1f) return
        canvas.drawRoundRect(getProgressRectF(), 0f, 0f, progressPaint!!)
    }

    private fun drawBorder(canvas: Canvas) {
        borderPaint?.color = borderColor
        canvas.drawRoundRect(bgRectF, borderRadius, borderRadius, borderPaint)
    }

    private fun drawVoteContentText(canvas: Canvas) {
        if (mVoteContent.isNullOrBlank()) return
        //文字绘制到整个布局的中心位置
        if (mProgress == -1f) {
            voteContentRectF.left = (mWidth - textWidth) / 2
            voteContentRectF.right = voteContentRectF.left + textWidth
        } else {
            voteContentRectF.left = max(((1 - mScale) * (mWidth - textWidth) / 2).toInt(), textMarginLeft)
            voteContentRectF.right = voteContentRectF.left + textWidth
        }

        canvas.drawText(mVoteContent, voteContentRectF.centerX().toFloat(), voteContentBaseline.toFloat(), voteContentTextPaint)
    }

    private fun drawVoteResultText(canvas: Canvas) {
        if (mProgress == -1f || mVoteResult.isNullOrBlank()) return
        //文字绘制到整个布局的中心位置
        voteResultTextPaint?.alpha = (255 * mScale).toInt()
        canvas.drawText(mVoteResult, mWidth - voteResultMarginRight - voteResultRectF.centerX().toFloat(), voteResultBaseline.toFloat(), voteResultTextPaint)
    }

    private fun drawVoteRightIcon(canvas: Canvas) {
        if (rightCheckedBitmapRes != null && isVoteChecked) {
            voteRightIconRectF.left = voteContentRectF.right + voteResultMarginRight
            voteRightIconRectF.right = voteRightIconRectF.left + rightIconWidth
            canvas.drawBitmap(rightCheckedBitmapRes!!, null, voteRightIconRectF, iconPaint)
        }
    }

    private fun getProgressRectF(): RectF {
//        val currentProgress = mProgress * mWidth * mScale / 100
        val currentProgress = mProgress * mWidth * mScale
        progressRectF.set(0f, 0f, currentProgress, mHeight.toFloat())
        return progressRectF
    }


    fun setVoteCheckedProgressColor(color: Int): VoteView {
        this.checkedProgressColor = color
        return this
    }

    fun setVoteUncheckedProgressColor(color: Int): VoteView {
        this.unCheckedProgressColor = color
        return this
    }

    fun setVoteBorderRadius(radius: Float): VoteView {
        this.borderRadius = radius
        return this
    }

    fun setVoteBorderColor(color: Int): VoteView {
        this.borderColor = color
        return this
    }

    fun setVoteCheckedContentTextColor(color: Int): VoteView {
        this.checkedContentTextColor = color
        return this
    }

    fun setVoteUncheckedContentTextColor(color: Int): VoteView {
        this.uncheckedContentTextColor = color
        return this
    }


    fun setVoteCheckedResultTextColor(color: Int): VoteView {
        this.checkedResultTextColor = color
        return this
    }

    fun setVoteUncheckedResultTextColor(color: Int): VoteView {
        this.uncheckedResultTextColor = color
        return this
    }

    fun setVoteCheckedIcon(iconBitmap: Drawable): VoteView {
        this.rightCheckedBitmapRes = (iconBitmap as? BitmapDrawable)?.bitmap
        return this
    }

    fun setVoteRightIconSize(width_height: Int): VoteView {
        this.rightIconWidth = width_height
        this.rightIconHeight = width_height
        return this
    }

    fun setVoteTextSize(textSize: Int): VoteView {
        this.textPaintSize = textSize
        return this
    }

    fun setVoteAnimDuration(duration: Long): VoteView {
        this.animDuration = duration
        return this
    }

    fun setVoteContent(content: String?): VoteView {
        mVoteContent = content ?: ""
        setVoteContentRect()
        return this
    }

    fun setVoteResultText(voteResult: String?): VoteView {
        mVoteResult = voteResult ?: ""
        setVoteResultRect()
        return this
    }

    fun refreshView() {
        initColor()
        invalidate()
    }


    fun setProgress(progress: Float) {
        mProgress = progress
        if (mProgress != -1f) {
            invalidate()
        }
    }

    fun setProgressWithAnim(progress: Float) {
        mProgress = progress
        startAnim()
    }

    private fun startAnim() {
        valueAnimator?.cancel()
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        }
        valueAnimator?.duration = animDuration
        valueAnimator?.interpolator = DecelerateInterpolator()
        valueAnimator?.addUpdateListener {
            mScale = it.animatedValue as Float
            invalidate()
        }
        valueAnimator?.start()
    }

    fun onDestroy() {
        valueAnimator?.cancel()
        valueAnimator = null
    }

    fun setVoteIsSelected(isVoteSelected: Boolean): VoteView {
        this.isVoteChecked = isVoteSelected
        return this
    }

    fun dp2px(dpVal: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, resources.displayMetrics)
    }

    fun sp2px(spVal: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal.toFloat(), resources.displayMetrics).toInt()
    }
}