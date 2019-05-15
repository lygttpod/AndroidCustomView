package com.allen.androidcustomview.widget.status

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2019/03/19
 *      desc    : 状态布局
 * </pre>
 */

class StatusLayout : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {

    }
}