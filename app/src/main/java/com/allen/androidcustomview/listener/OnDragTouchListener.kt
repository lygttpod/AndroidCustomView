package com.allen.androidcustomview.listener

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2019/03/14
 *      desc    :
 * </pre>
 */
class OnDragTouchListener : View.OnTouchListener {

    //手指按下时的初始位置
    private var mOriginalX: Float = 0f
    private var mOriginalY: Float = 0f
    //记录手指与view的左上角的距离
    private var mDistanceX: Float = 0f
    private var mDistanceY: Float = 0f
    //拖拽view的上下左右距离
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0
    //最小的拖拽距离,小于这个值认为不是拖拽，区分是点击还是拖拽
    private var minDragDistance = 10f
    private var mLayoutParams: ViewGroup.MarginLayoutParams? = null

    //可拖拽屏幕区域宽高
    var mMaxWidth: Int = 0
    var mMaxHeight: Int = 0

    //点击事件
    var clickListener: (() -> Unit)? = null

    //标记是否自动吸附到边缘
    var mIsAutoToBorder = true
    //吸附在边缘时候距离边界的距离
    var mBorderMargin = 0f

    var mBorderMarginLeft = -1f
    var mBorderMarginRight = -1f
    var mBorderMarginTop = -1f
    var mBorderMarginBottom = -1f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                view.parent.requestDisallowInterceptTouchEvent(true)

                mLayoutParams = mLayoutParams ?: view.layoutParams as ViewGroup.MarginLayoutParams

                mOriginalX = event.rawX
                mOriginalY = event.rawY

                mDistanceX = event.rawX - view.left
                mDistanceY = event.rawY - view.top
            }

            MotionEvent.ACTION_MOVE -> {
                left = (event.rawX - mDistanceX).toInt()
                top = (event.rawY - mDistanceY).toInt()
                right = left + view.width
                bottom = top + view.height
                if (left < 0) {
                    left = 0
                    right = left + view.width
                }
                if (top < 0) {
                    top = 0
                    bottom = top + view.height
                }
                if (right > mMaxWidth) {
                    right = mMaxWidth
                    left = right - view.width
                }
                if (bottom > mMaxHeight) {
                    bottom = mMaxHeight
                    top = bottom - view.height
                }

                //如果其他view刷新导致重绘会调用layout方法，导致位置一闪一闪的,所有要用layoutParams设置位置
                //view.layout(left, top, right, bottom)
                mLayoutParams?.setMargins(left, top, 0, 0)
                view.layoutParams = mLayoutParams
            }
            MotionEvent.ACTION_UP -> {
                //如果移动距离过小，则判定为点击
                if (Math.abs(event.rawX - mOriginalX) < minDragDistance && Math.abs(event.rawY - mOriginalY) < minDragDistance) {
                    clickListener?.invoke()
                } else {
                    setAutoToBorder(view)
                }
                view.parent.requestDisallowInterceptTouchEvent(false)
                //调取performClick()方法消除警告OnDragTouchListener#onTouch should call View#performClick when a click is detected more...
                view.performClick()
            }
        }
        return true
    }


    /**
     * 开启自动拖拽
     *
     * @param v  拉动控件
     */
    private fun setAutoToBorder(v: View) {
        if (!mIsAutoToBorder) return
        setAnimation(v)
    }

    private fun setAnimation(v: View) {
        val animatorSet = AnimatorSet()
        if (getTopOrBottomAnimation(v) == null) {
            animatorSet.play(getLeftOrRightAnimation(v))
        } else {
            animatorSet.play(getLeftOrRightAnimation(v)).with(getTopOrBottomAnimation(v))
        }
        animatorSet.duration = 300
        animatorSet.start()
    }


    /**
     * 获取吸附在左右边界的动画
     */
    private fun getLeftOrRightAnimation(v: View): Animator {
        //当用户拖拽完后，让控件回到最近的边缘
        var leftOrRightEnd = getBorderMargin(mBorderMarginLeft)
        //吸附在右边边界处
        if (left + v.width / 2 >= mMaxWidth / 2) {
            leftOrRightEnd = (mMaxWidth - v.width - getBorderMargin(mBorderMarginRight))
        }
        val animator = ValueAnimator.ofFloat(left.toFloat(), leftOrRightEnd)
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val leftMargin = (animation.animatedValue as Float).toInt()
            mLayoutParams?.leftMargin = leftMargin
            v.layoutParams = mLayoutParams
        }
        return animator
    }

    /**
     * 获取吸附在顶部或者底部的动画
     */
    private fun getTopOrBottomAnimation(v: View): Animator? {
        //吸附在上下边界处
        var topOrBottomEnd: Float
        //吸附在下边界处
        return when {
            //吸附到距离底部mBorderMargin的距离的位置
            top + v.height >= mMaxHeight - mBorderMargin -> {
                topOrBottomEnd = mMaxHeight - v.height - getBorderMargin(mBorderMarginBottom)
                createTopOrBottomAnimation(v, topOrBottomEnd)
            }
            //吸附到距离顶部mBorderMargin的距离的位置
            top <= mBorderMargin -> {
                topOrBottomEnd = getBorderMargin(mBorderMarginTop)
                createTopOrBottomAnimation(v, topOrBottomEnd)
            }
            else -> null
        }
    }


    /**
     * 创建底吸附到底部或顶部的动画
     */
    private fun createTopOrBottomAnimation(view: View, end: Float): Animator? {
        val animator = ValueAnimator.ofFloat(top.toFloat(), end)
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val topMargin = (animation.animatedValue as Float).toInt()
            mLayoutParams?.topMargin = topMargin
            view.layoutParams = mLayoutParams
        }
        return animator
    }

    /**
     * 获取吸附的边界值
     */
    private fun getBorderMargin(margin: Float): Float {
        return if (margin != -1f) margin else mBorderMargin
    }

}