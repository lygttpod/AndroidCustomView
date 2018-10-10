package com.allen.androidcustomview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.allen.androidcustomview.R;

/**
 * <pre>
 *      @author : Allen
 *      date    : 2018/09/30
 *      desc    : 支付宝首页 来源 https://mp.weixin.qq.com/s/GegMt7GDBCFVoUgFQWG3Sw
 *      version : 1.0
 * </pre>
 */
public class AliPayHomeActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    

    private AppBarLayout mAppBarLayout;
    private View mToolbarOpenBgView;
    private View mToolbarCloseBgView;
    private View mToolbarOpenLayout;
    private View mToolbarCloseLayout;
    private View contentBgView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alipay_home);
        initView();
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void initView() {

        mToolbarOpenBgView = findViewById(R.id.toolbar_open_bg_view);
        mToolbarOpenLayout = findViewById(R.id.include_toolbar_open);

        mToolbarCloseBgView =  findViewById(R.id.bg_toolbar_close);
        mToolbarCloseLayout =  findViewById(R.id.include_toolbar_close);
        contentBgView =  findViewById(R.id.content_bg_view);

        mAppBarLayout =  findViewById(R.id.app_bar_layout);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            mToolbarOpenLayout.setVisibility(View.VISIBLE);
            mToolbarCloseLayout.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            mToolbarOpenBgView.setBackgroundColor(Color.argb(alpha2, 25, 131, 209));
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            mToolbarOpenLayout.setVisibility(View.GONE);
            mToolbarCloseLayout.setVisibility(View.VISIBLE);
            float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            mToolbarCloseBgView.setBackgroundColor(Color.argb(alpha3, 25, 131, 209));
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        contentBgView.setBackgroundColor(Color.argb(alpha, 25, 131, 209));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }
}
