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
abstract class SuperItemAnimation(animDuration: Long = 200) : BaseItemAnimation() {

    init {
        addDuration = animDuration
        removeDuration = animDuration
    }

    abstract fun setAddItemAnimInit(holder: RecyclerView.ViewHolder?)
    abstract fun setAddItemAnim(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?)
    abstract fun setAddItemAnimCancel(holder: RecyclerView.ViewHolder?)

    abstract fun setRemoveItemAnim(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?)
    abstract fun setRemoveItemAnimEnd(holder: RecyclerView.ViewHolder?)


    override fun setAddItemAnimationInit(holder: RecyclerView.ViewHolder?) {
        setAddItemAnimInit(holder)
    }

    override fun setAddItemAnimation(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        setAddItemAnim(holder, animator)
    }

    override fun setAddItemAnimationCancel(holder: RecyclerView.ViewHolder?) {
        setAddItemAnimCancel(holder)
    }

    override fun setRemoveAnimation(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        setRemoveItemAnim(holder, animator)
    }

    override fun setRemoveAnimationEnd(holder: RecyclerView.ViewHolder?) {
        setRemoveItemAnimEnd(holder)
    }


    override fun setOldChangeAnimation(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        animator?.alpha(0f)
    }

    override fun setOldChangeAnimationEnd(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.alpha = 1f
    }

    override fun setNewChangeAnimationInit(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.alpha = 0f
    }

    override fun setNewChangeAnimation(holder: RecyclerView.ViewHolder?, animator: ViewPropertyAnimator?) {
        animator?.alpha(1f)
    }

    override fun setNewChangeAnimationEnd(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.alpha = 1f
    }
}