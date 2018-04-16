package com.allen.androidcustomview.widget.banner.holder;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/03/02
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public interface BannerViewHolderCreator<VH extends BannerViewHolder> {
    /**
     * 创建 BannerViewHolder
     *
     * @return BannerViewHolder
     */
    VH createViewHolder();
}
