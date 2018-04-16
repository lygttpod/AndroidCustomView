package com.allen.androidcustomview.widget.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/03/02
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public interface BannerViewHolder<T> {

    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);

}
