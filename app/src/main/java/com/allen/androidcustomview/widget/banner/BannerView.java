package com.allen.androidcustomview.widget.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.widget.banner.adapter.BannerViewPagerAdapter;
import com.allen.androidcustomview.widget.banner.holder.BannerViewHolderCreator;

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

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG = BannerView.class.getSimpleName();

    public ViewPager getViewPager() {
        return mViewPager;
    }

    private ViewPager mViewPager;
    private LinearLayout mIndicatorContainer;
    private BannerViewPagerAdapter<?> mAdapter;

    private PagerOptions mPagerOptions;
    private ImageView mCurrentIndicator;

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.banner_view_layout, this);
        mViewPager = (ViewPager) findViewById(R.id.banner_view_pager);
        mIndicatorContainer = (LinearLayout) findViewById(R.id.banner_indicator_container);

        mPagerOptions = new PagerOptions.Builder(context).build();

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 切换indicator
        setIndicatorSelected(position);

        setOnPageSelectedListener(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void setOnPageSelectedListener(int position) {
        if (mPagerOptions.mOnPageChangeListener != null) {
            mPagerOptions.mOnPageChangeListener.onPageSelected(position);
        }
    }

    /**
     * 设置 PagerOptions
     *
     * @param options options
     * @return BannerPager<T>
     */
    public BannerView setPagerOptions(PagerOptions options) {
        mPagerOptions = options;
        return this;
    }

    /**
     * 设置 page data
     *
     * @param data    List<T>
     * @param creator BannerViewHolderCreator
     */
    public void setPages(@NonNull List<?> data, @NonNull BannerViewHolderCreator creator) {
        if (data == null || creator == null) {
            return;
        }
        mAdapter = new BannerViewPagerAdapter<>(data, creator);

        initIndicator();
        handlePagerOptions();

        mViewPager.setAdapter(mAdapter);


    }

    private void initIndicator() {

        int count = mAdapter.getRealCount();

        mIndicatorContainer.removeAllViews();
        ImageView indicator;
        LinearLayout.LayoutParams layoutParams;

        for (int i = 0; i < count; i++) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,0,10,0);
            indicator = new ImageView(getContext());
            indicator.setImageDrawable(mPagerOptions.mIndicatorDrawable[0]);
            indicator.setLayoutParams(layoutParams);
            mIndicatorContainer.addView(indicator);
        }
        setIndicatorSelected(mViewPager.getCurrentItem());

    }

    private void setIndicatorSelected(int currentItem) {
        if (mCurrentIndicator != null) {
            mCurrentIndicator.setImageDrawable(mPagerOptions.mIndicatorDrawable[0]);
            mCurrentIndicator.setSelected(false);
        }
        if (mIndicatorContainer.getChildCount()>0){
            final ImageView indicator = (ImageView) mIndicatorContainer.getChildAt(currentItem);
            indicator.setSelected(true);
            indicator.setImageDrawable(mPagerOptions.mIndicatorDrawable[1]);
            mCurrentIndicator = indicator;
        }
    }

    private void handlePagerOptions() {
        //设置每页之间间距
        mViewPager.setPageMargin(mPagerOptions.mPageMargin);
        //设置预显示宽
        final ViewGroup.MarginLayoutParams mp = (MarginLayoutParams) mViewPager.getLayoutParams();
        mp.leftMargin = mp.rightMargin = mPagerOptions.mPrePagerWidth;
        mViewPager.setLayoutParams(mp);

        //设置切换效果
        mViewPager.setPageTransformer(true, mPagerOptions.mPageTransformer);
        mAdapter.setPageClickListener(mPagerOptions.mOnPageClickListener);

    }

}
