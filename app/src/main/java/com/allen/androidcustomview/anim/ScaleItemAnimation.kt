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
class ScaleItemAnimation(animDuration: Long = 500) : SuperItemAnimation(animDuration) {

    override fun setAddItemAnimInit(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.scaleX = 0f
        holder?.itemView?.scaleY = 0f
    }

    override fun setAddItemAnim(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        holder?.itemView?.pivotX = 0f
        holder?.itemView?.pivotY = 0f
        animator?.scaleX(1f)?.scaleY(1f)
    }

    override fun setAddItemAnimCancel(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.scaleX = 1f
        holder?.itemView?.scaleY = 1f
    }

    override fun setRemoveItemAnim(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        holder?.itemView?.pivotX = 1f
        holder?.itemView?.pivotY = 1f
        animator?.scaleX(0f)?.scaleY(0f)
    }

    override fun setRemoveItemAnimEnd(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.scaleX = 1f
        holder?.itemView?.scaleY = 1f
    }

}