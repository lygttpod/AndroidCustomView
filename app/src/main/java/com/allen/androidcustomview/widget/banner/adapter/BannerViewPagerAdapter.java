package com.allen.androidcustomview.widget.banner.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.allen.androidcustomview.widget.banner.listener.OnPageClickListener;
import com.allen.androidcustomview.widget.banner.holder.BannerViewHolder;
import com.allen.androidcustomview.widget.banner.holder.BannerViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/03/02
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class BannerViewPagerAdapter<T> extends PagerAdapter {

    private List<T> mData;
    private BannerViewHolderCreator mCreator;
    private OnPageClickListener mPageClickListener;

    public BannerViewPagerAdapter(@NonNull List<T> data, @NonNull BannerViewHolderCreator creator) {

        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData = data;
        mCreator = creator;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = getView(position, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    /**
     * 获取真实的Count
     *
     * @return
     */
    public int getRealCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getCount() {
        return getRealCount();
    }

    /**
     * @param position
     * @param container
     * @return
     */
    private View getView(int position, ViewGroup container) {

        final int realPosition = position % getRealCount();
        BannerViewHolder holder = null;
        // create holder
        holder = mCreator.createViewHolder();

        if (holder == null) {
            throw new RuntimeException("can not return a null holder");
        }
        // create View
        View view = holder.createView(container.getContext());

        if (mData != null && mData.size() > 0) {
            holder.onBind(container.getContext(), realPosition, mData.get(realPosition));
        }

        // 添加page点击事件
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPageClickListener != null) {
                        mPageClickListener.onPageClick(v, realPosition);
                    }
                }
            });
        }

        return view;
    }


    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
    }

    public void setPageClickListener(OnPageClickListener pageClickListener) {
        this.mPageClickListener = pageClickListener;
    }
}
