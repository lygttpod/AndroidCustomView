package com.allen.androidcustomview.widget.banner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.allen.androidcustomview.widget.banner.listener.OnPageChangeListener;
import com.allen.androidcustomview.widget.banner.listener.OnPageClickListener;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/03/02
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class PagerOptions {


    int mPageMargin;
    int mPrePagerWidth;
    int mIndicatorVisibility;
    Drawable[] mIndicatorDrawable;
    int mIndicatorDistance;
    boolean mLoopEnable;
    int mDelayedTime;
    int mIndicatorAlign;
    ViewPager.PageTransformer mPageTransformer;
    int mScrollDuration;
    int mIndicatorMarginBottom;
    int mIndicatorSize;
    OnPageClickListener mOnPageClickListener;
    OnPageChangeListener mOnPageChangeListener;

    private PagerOptions() {
    }

    public static class Builder {

        private Context mContext;
        private int mPageMargin;
        private int mPrePagerWidth;
        private int mIndicatorAlign;
        private int mIndicatorVisibility;
        private Drawable[] mIndicatorDrawable = new Drawable[2];
        private int mIndicatorDistance = 8;
        private boolean mLoopEnable = true;
        private ViewPager.PageTransformer mPageTransformer;
        private int mDelayedTime = 3000;
        private int mScrollDuration = 800;
        private int mIndicatorMarginBottom = -1;
        private int mIndicatorSize = -1;
        private OnPageClickListener mOnPageClickListener;
        private OnPageChangeListener mOnPageChangeListener;

        public Builder(Context context) {
            mContext = context;
            //设置默认指示器
//            setIndicatorDrawable(R.drawable.indicator_normal_default, R.drawable.indicator_selected_default);
        }


        /**
         * 设置每个 page 之间间隔
         *
         * @param px px value
         * @return Builder
         */
        public Builder setPageMargin(int px) {
            mPageMargin = px;
            return this;
        }

        /**
         * 左右两侧预显示宽度
         *
         * @param px px value
         * @return Builder
         */
        public Builder setPrePagerWidth(int px) {
            mPrePagerWidth = px;
            return this;
        }

        /**
         * 设置指示器间距
         *
         * @param distance px value
         * @return Builder
         */
        public Builder setIndicatorDistance(int distance) {
            mIndicatorDistance = distance;
            return this;
        }

        /**
         * 设置指示器距离底部间距
         *
         * @param marginBottom marginBottom
         * @return Builder
         */
        public Builder setIndicatorMarginBottom(int marginBottom) {
            mIndicatorMarginBottom = marginBottom;
            return this;
        }

        /**
         * 设置指示器位置
         *
         * @param align RelativeLayout.ALIGN_PARENT_LEFT || RelativeLayout.CENTER_IN_PARENT || RelativeLayout.ALIGN_PARENT_RIGHT
         * @return Builder
         */
        public Builder setIndicatorAlign(int align) {
            mIndicatorAlign = align;
            return this;
        }

        /**
         * 设置Indicator 是否可见
         *
         * @param visibility One of VISIBLE, INVISIBLE, GONE.
         * @return Builder
         */
        public Builder setIndicatorVisibility(int visibility) {
            mIndicatorVisibility = visibility;
            return this;
        }

        /**
         * 设置轮播切换效果
         *
         * @param transformer PageTransformer
         * @return Builder
         */
        public Builder setPageTransformer(ViewPager.PageTransformer transformer) {
            mPageTransformer = transformer;
            return this;
        }

        /**
         * 设置指示器
         *
         * @param unSelected 未选中
         * @param selected   选中
         * @return Builder
         */
        public Builder setIndicatorDrawable(@DrawableRes int unSelected, @DrawableRes int selected) {
            mIndicatorDrawable[0] = ContextCompat.getDrawable(mContext, unSelected);
            mIndicatorDrawable[1] = ContextCompat.getDrawable(mContext, selected);
            return this;
        }

        /**
         * 设置指示器
         *
         * @param unSelected 未选中
         * @param selected   选中
         * @return Builder
         */
        public Builder setIndicatorColor(@ColorInt int unSelected, @ColorInt int selected) {
            mIndicatorDrawable[0] = createDrawable(unSelected);
            mIndicatorDrawable[1] = createDrawable(selected);
            return this;
        }

        private Drawable createDrawable(@ColorInt int color) {
            final int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mContext.getResources().getDisplayMetrics());
            final GradientDrawable gd = new GradientDrawable();
            gd.setColor(color);
            gd.setShape(GradientDrawable.OVAL);
            gd.setSize(size, size);
            return gd;
        }

        /**
         * 设置指示器
         *
         * @param size px
         * @return Builder
         */
        public Builder setIndicatorSize(int size) {
            mIndicatorSize = size;
            return this;
        }

        /**
         * 设置可否循环
         *
         * @param loop loop
         * @return Builder
         */
        public Builder setLoopEnable(boolean loop) {
            mLoopEnable = loop;
            return this;
        }

        /**
         * 设置切换时间
         *
         * @param duration ms
         * @return Builder
         */
        public Builder setTurnDuration(int duration) {
            mDelayedTime = duration;
            return this;
        }

        /**
         * 设置ViewPager的滚动速度
         *
         * @param duration ms
         */
        public Builder setScrollDuration(int duration) {
            mScrollDuration = duration;
            return this;
        }


        public Builder setOnPageClickListener(OnPageClickListener onPageClickListener) {
            this.mOnPageClickListener = onPageClickListener;
            return this;
        }

        public Builder setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
            this.mOnPageChangeListener = onPageChangeListener;
            return this;
        }

        public PagerOptions build() {
            final PagerOptions options = new PagerOptions();

            options.mPageMargin = mPageMargin;
            options.mPrePagerWidth = mPrePagerWidth;
            options.mIndicatorDistance = mIndicatorDistance;
            options.mIndicatorDrawable = mIndicatorDrawable;
            options.mIndicatorSize = mIndicatorSize;
            options.mIndicatorAlign = mIndicatorAlign;
            options.mIndicatorVisibility = mIndicatorVisibility;
            options.mLoopEnable = mLoopEnable;
            options.mPageTransformer = mPageTransformer;
            options.mDelayedTime = mDelayedTime;
            options.mScrollDuration = mScrollDuration;
            options.mIndicatorMarginBottom = mIndicatorMarginBottom;
            options.mOnPageClickListener = mOnPageClickListener;
            options.mOnPageChangeListener = mOnPageChangeListener;
            mContext = null;
            return options;
        }
    }

}
