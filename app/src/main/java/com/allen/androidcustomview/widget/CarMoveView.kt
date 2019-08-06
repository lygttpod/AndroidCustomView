package com.allen.androidcustomview.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.allen.androidcustomview.R
import kotlin.math.atan2


/**
 * <pre>
 *      @author : Allen
 *      date    : 2019/07/23
 *      desc    : 小汽车跟随轨迹运动
 * </pre>
 */
class CarMoveView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0

    /**
     * 背景颜色
     */
    private var mPathColor = Color.RED
    private var mPaint = Paint()
    private var mCarPaint = Paint()

    private var mRect = Rect()
    private var mMovePath = Path()

    private var mStartX = 0f
    private var mStartY = 0f

    private var pathMeasure: PathMeasure = PathMeasure()

    private val pos = FloatArray(2)
    private val tan = FloatArray(2)

    private var isMoveCar = false

    private var mRectWidth = 30

    private var mDuration = 5
    private var mCarDrawableRes: Drawable? = null
    private var mCarBitmapRes: Bitmap? = null

    init {
        initView(context, attrs, defStyleAttr)
        initPaint()
        mCarBitmapRes = (mCarDrawableRes as? BitmapDrawable)?.bitmap
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CarMoveView)
        mPathColor = typedArray.getColor(R.styleable.CarMoveView_carMovePathColor, mPathColor)
        mDuration = typedArray.getInt(R.styleable.CarMoveView_carMoveDuration, mDuration)
        mCarDrawableRes = typedArray.getDrawable(R.styleable.CarMoveView_carMoveDrawableRes)
        typedArray.recycle()
    }

    private fun initPaint() {
        mPaint.color = mPathColor
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        mCarPaint.color = mPathColor
        mCarPaint.style = Paint.Style.FILL
        mCarPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawPath(canvas)
        drawMoveCar(canvas)
    }

    private fun drawPath(canvas: Canvas?) {
        canvas?.drawPath(mMovePath, mPaint)
    }

    private fun drawMoveCar(canvas: Canvas?) {
        pathMeasure.setPath(mMovePath, false)
        if (isMoveCar) {
            // 计算图片旋转角度
            val degrees = (atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0 / Math.PI).toFloat()
            canvas?.rotate(degrees, pos[0], pos[1])
            //小车中心点在运行轨道上
            mRect.set((pos[0] - mRectWidth).toInt(), (pos[1] - mRectWidth).toInt(), (pos[0] + mRectWidth).toInt(), (pos[1] + mRectWidth).toInt())
            //小车轮子在运行轨道上
            //mRect.set((pos[0] - mRectWidth).toInt(), (pos[1] - mRectWidth * 2).toInt(), (pos[0] + mRectWidth).toInt(), (pos[1]).toInt())
            if (mCarBitmapRes == null) {
                canvas?.drawRect(mRect, mCarPaint)
            } else {
                canvas?.drawBitmap(mCarBitmapRes, null, mRect, mPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event.x
                mStartY = event.y
                mMovePath.reset()
                mMovePath.moveTo(mStartX, mStartY)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (mStartX + event.x) / 2
                val endY = (mStartY + event.y) / 2
                mMovePath.quadTo(mStartX, mStartY, endX, endY)
                mStartX = event.x
                mStartY = event.y
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun startAnim() {
        isMoveCar = true
        val valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
        valueAnimator.duration = mDuration * 1000L
        valueAnimator.addUpdateListener {
            val distance: Float = it.animatedValue as Float
            pathMeasure.getPosTan(distance, pos, tan)
            invalidate()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                isMoveCar = false
            }

            override fun onAnimationCancel(animation: Animator?) {
                isMoveCar = false
            }

            override fun onAnimationStart(animation: Animator?) {}

        })
        valueAnimator.start()
    }
}