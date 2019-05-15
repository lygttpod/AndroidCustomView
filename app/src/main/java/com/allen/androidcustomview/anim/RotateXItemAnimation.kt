package com.allen.androidcustomview.anim

import android.support.v7.widget.RecyclerView
import android.view.ViewPropertyAnimator

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2019/05/15
 *      desc    :
 * </pre>
 */
class RotateXItemAnimation(animDuration: Long = 500) : SuperItemAnimation(animDuration) {

    init {
        addDuration = animDuration
        removeDuration = animDuration
    }

    override fun setAddItemAnimInit(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.rotationX = -90f
    }

    override fun setAddItemAnim(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        animator?.rotationX(0f)
    }

    override fun setAddItemAnimCancel(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.rotationX = 0f
    }

    override fun setRemoveItemAnim(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        animator?.rotationX(-90f)
    }

    override fun setRemoveItemAnimEnd(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.rotationX = 0f
    }

}