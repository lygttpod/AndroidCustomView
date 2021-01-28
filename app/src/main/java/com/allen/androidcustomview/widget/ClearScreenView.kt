package com.allen.androidcustomview.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlin.math.abs


/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2021/01/28
 *      desc    : 滑动清屏view
 * </pre>
 */
enum class ClearScreenType {
    LEFT_TO_RIGHT,//从左滑到右清屏
    RIGHT_TO_LEFT //从右滑到左清屏
}

enum class ClearScreenStatus {
    NORMAL,//正常状态
    CLEARED//已经清屏状态
}

enum class ClearScreenMode {
    QUICK_SCROLL,//快速滑动才触发清屏
    SLOW_SCROLL//滑动出发清屏
}

class ClearScreenView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(mContext, attrs, defStyleAttr) {

    companion object {
        /**
         * 最小移动距离
         */
        private const val MIN_SCROLL_SIZE = 50

        /**
         * 水平方向最小滑动速度
         */
        private const val MIN_X_VELOCITY = 10

        /**
         * 清屏动画时长
         */
        private const val DURATION = 300L
    }

    /**
     * 手指按下的x轴位置
     */
    private var mDownX = 0

    /**
     * 手指按下的y轴位置
     */
    private var mDownY = 0

    /**
     * 清屏view清屏时需要在x轴的偏移量
     */
    private var translateX = 0

    /**
     * 清屏view起始偏移量（例如：从无到有迁移量从-width到0）
     */
    private var startTranslateX = 0

    /**
     * 滑动速度对象
     */
    private var mVelocityTracker: VelocityTracker? = null

    /**
     * 清屏动画对象
     */
    private var mAnimator: ValueAnimator? = null

    /**
     * 需要清除的Views
     */
    private var listClearViews: ArrayList<View> = ArrayList()

    /**
     * 清屏事件
     */
    private var clearScreenListener: OnClearScreenListener? = null

    /**
     * 清屏类型 左滑清屏 or 右滑清屏  (默认从左滑到右清屏)
     */
    private var clearScreenType = ClearScreenType.LEFT_TO_RIGHT

    /**
     * 当前清屏状态
     */
    private var clearScreenStatus = ClearScreenStatus.NORMAL

    /**
     * 清屏模式
     */
    var clearScreenMode = ClearScreenMode.QUICK_SCROLL

    /**
     * 是否正在处在滑动清屏状态
     */
    private var isScrolling = false

    init {
        initView()
        initAnim()
    }

    private fun initView() {
        val view = View(mContext)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.isClickable = true
        addView(view, 0)
    }

    private fun initAnim() {
        mVelocityTracker = VelocityTracker.obtain()
        mAnimator = ValueAnimator.ofFloat(0f, 1.0f).setDuration(DURATION)
        mAnimator?.addUpdateListener {
            val value = it.animatedValue as Float
            val translate = startTranslateX + value * translateX
            translateChild(translate)
        }
        mAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                when (clearScreenStatus) {
                    ClearScreenStatus.CLEARED -> {
                        clearScreenStatus = ClearScreenStatus.NORMAL
                        clearScreenListener?.onRestored()
                    }
                    ClearScreenStatus.NORMAL -> {
                        clearScreenStatus = ClearScreenStatus.CLEARED
                        clearScreenListener?.onCleared()
                    }
                }
                isScrolling = false
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (clearScreenListener?.isForbidClearScreen() == true) {
            return super.onInterceptTouchEvent(ev)
        }
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = x
                mDownY = y
            }
        }
        return isInterceptClearScreenEvent(x, y)
    }

    private fun isInterceptClearScreenEvent(x: Int, y: Int): Boolean {
        return when (clearScreenMode) {
            ClearScreenMode.QUICK_SCROLL -> {
                val isIntercept = isMoveForHorizontal(x, y) && !isAnimRunning() && isGreaterThanMinSize(mDownX, x)
                requestDisallowInterceptTouchEvent(isIntercept)
                isIntercept
            }
            ClearScreenMode.SLOW_SCROLL -> {
                val isIntercept = isMoveForHorizontal(x, y) && !isAnimRunning() || isScrolling
                requestDisallowInterceptTouchEvent(isIntercept)
                isIntercept
            }
        }
    }

    /**
     * 是否水平方向滑动
     */
    private fun isMoveForHorizontal(x: Int, y: Int): Boolean {
        return abs(x - mDownX) > abs(y - mDownY)
    }

    private fun isAnimRunning(): Boolean {
        return mAnimator?.isRunning == true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isAnimRunning()) return true
        when (clearScreenMode) {
            ClearScreenMode.QUICK_SCROLL -> {
                handleQuickScrollMode(event)
            }
            ClearScreenMode.SLOW_SCROLL -> {
                handleSlowScrollMode(event)
            }
        }
        return true
    }

    private fun handleQuickScrollMode(event: MotionEvent) {
        mVelocityTracker?.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                mVelocityTracker?.computeCurrentVelocity(10)
                val xVelocity = mVelocityTracker?.xVelocity ?: 0f
                when {
                    //从左往右滑动(是正数)速度大于阈值
                    xVelocity > MIN_X_VELOCITY -> {
                        setLeft2RightMoveTranslateX()
                    }
                    //从右往左滑动(是负数)速度大于阈值
                    xVelocity < -MIN_X_VELOCITY -> {
                        setRight2LeftMoveTranslateX()
                    }
                    else -> {
                        translateX = 0
                    }
                }
                if (translateX != 0) {
                    mAnimator?.start()
                }
            }
        }
    }

    private fun handleSlowScrollMode(event: MotionEvent) {
        val x = event.x.toInt()
        when (event.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                when (clearScreenStatus) {
                    ClearScreenStatus.NORMAL -> {
                        if (startTranslateX >= width / 2) {
                            translateX = width - startTranslateX
                            clearScreenStatus = ClearScreenStatus.NORMAL
                        } else {
                            translateX = -startTranslateX
                            clearScreenStatus = ClearScreenStatus.CLEARED
                        }
                        mAnimator?.start()
                    }
                    ClearScreenStatus.CLEARED -> {
                        if (startTranslateX >= width / 2) {
                            translateX = width - startTranslateX
                            clearScreenStatus = ClearScreenStatus.NORMAL
                        } else {
                            translateX = (-startTranslateX)
                            clearScreenStatus = ClearScreenStatus.CLEARED
                        }
                        mAnimator?.start()
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                translateX = 0
                val move = x - mDownX
                when (clearScreenStatus) {
                    ClearScreenStatus.NORMAL -> {
                        val translate = if (x <= mDownX) 0 else move
                        startTranslateX = translate
                        if (translate != 0) {
                            translateChild(translate.toFloat())
                        }
                    }
                    ClearScreenStatus.CLEARED -> {
                        val translate = if (x > mDownX) 0 else mDownX - x
                        startTranslateX = width - translate
                        if (startTranslateX != 0) {
                            translateChild(startTranslateX.toFloat())
                        }
                    }
                }
            }
        }
    }

    private fun setLeft2RightMoveTranslateX() {
        when (clearScreenType) {
            ClearScreenType.LEFT_TO_RIGHT -> {
                startTranslateX = 0
                translateX = if (clearScreenStatus == ClearScreenStatus.NORMAL) width else 0
            }
            ClearScreenType.RIGHT_TO_LEFT -> {
                startTranslateX = -width
                translateX = if (clearScreenStatus == ClearScreenStatus.NORMAL) 0 else width
            }
        }
    }

    private fun setRight2LeftMoveTranslateX() {
        when (clearScreenType) {
            ClearScreenType.LEFT_TO_RIGHT -> {
                startTranslateX = width
                translateX = if (clearScreenStatus == ClearScreenStatus.NORMAL) 0 else -width
            }
            ClearScreenType.RIGHT_TO_LEFT -> {
                startTranslateX = 0
                translateX = if (clearScreenStatus == ClearScreenStatus.NORMAL) -width else 0
            }
        }
    }

    /**
     * 是否大于清屏方向的最小值
     */
    private fun isGreaterThanMinSize(downX: Int, moveX: Int): Boolean {
        return when (clearScreenType) {
            ClearScreenType.LEFT_TO_RIGHT -> {
                when (clearScreenStatus) {
                    ClearScreenStatus.NORMAL -> moveX - downX > MIN_SCROLL_SIZE
                    ClearScreenStatus.CLEARED -> downX - moveX > MIN_SCROLL_SIZE
                }
            }
            ClearScreenType.RIGHT_TO_LEFT -> {
                when (clearScreenStatus) {
                    ClearScreenStatus.NORMAL -> downX - moveX > MIN_SCROLL_SIZE
                    ClearScreenStatus.CLEARED -> moveX - downX > MIN_SCROLL_SIZE
                }
            }
        }
    }

    private fun translateChild(translate: Float) {
        isScrolling = true
        for (view in listClearViews) {
            view.translationX = translate
        }
    }

    fun addClearViews(views: List<View>) {
        for (view in views) {
            if (!listClearViews.contains(view)) {
                listClearViews.add(view)
            }
        }
    }

    fun addClearView(view: View) {
        if (!listClearViews.contains(view)) {
            listClearViews.add(view)
        }
    }

    fun removeClearViews(views: List<View>) {
        for (view in views) {
            listClearViews.remove(view)
        }
    }

    fun removeAllClearViews() {
        listClearViews.clear()
    }

    fun setOnClearScreenListener(listener: OnClearScreenListener?) {
        this.clearScreenListener = listener
    }

    interface OnClearScreenListener {
        fun onCleared()
        fun onRestored()
        fun isForbidClearScreen(): Boolean = false
    }
}