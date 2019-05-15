package com.allen.androidcustomview.adapter

import com.allen.androidcustomview.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * <pre>
 *      @author : Allen
 *      e-mail  :lygttpod@163.com
 *      date    : 2019/05/11
 *      desc    :
 * </pre>
 */
class ItemAnimAdapter(list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_danmu_layout, list) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.content_tv, item)
    }
}