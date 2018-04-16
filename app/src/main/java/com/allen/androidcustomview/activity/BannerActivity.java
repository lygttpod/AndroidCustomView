package com.allen.androidcustomview.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.widget.banner.BannerView;
import com.allen.androidcustomview.widget.banner.listener.OnPageChangeListener;
import com.allen.androidcustomview.widget.banner.listener.OnPageClickListener;
import com.allen.androidcustomview.widget.banner.PagerOptions;
import com.allen.androidcustomview.widget.banner.holder.BannerViewHolder;
import com.allen.androidcustomview.widget.banner.holder.BannerViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/03/09
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class BannerActivity extends AppCompatActivity {

    private BannerView bannerView;
    private List<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        datas.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
        datas.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180102083655_3t4ytm_Screenshot.jpeg");
        datas.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg");

        bannerView = (BannerView) findViewById(R.id.banner_view);

        PagerOptions options  = new PagerOptions
                .Builder(this)
                .setPageMargin(20)
                .setPrePagerWidth(50)
                .setIndicatorDrawable(R.mipmap.banner_point_disabled,R.mipmap.banner_point_enabled)
//                .setIndicatorColor(Color.YELLOW,Color.RED)
                .setOnPageClickListener(new OnPageClickListener() {
                    @Override
                    public void onPageClick(View view, int position) {
                        Toast.makeText(BannerActivity.this,"Click"+position,Toast.LENGTH_SHORT).show();
                    }
                })
                .build();


        bannerView.setPagerOptions(options)
                  .setPages(datas, new BannerViewHolderCreator<MyBanner>() {
                    @Override
                    public MyBanner createViewHolder() {
                        return new MyBanner();
                    }
                });

    }

    public class MyBanner implements BannerViewHolder<String>{

        private ImageView imageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {

        }
    }
}
