package com.allen.androidcustomview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/05/07
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class ItemCenterRecyclerView extends RecyclerView {


    public ItemCenterRecyclerView(Context context) {
        super(context);
    }

    public ItemCenterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemCenterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        int childCount = getChildCount();
        Log.e("allen", childCount + "");

        int[] location = new int[2];
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.getLocationOnScreen(location);

            int recyclerViewCenterX = getLeft() + getWidth() / 2;
            int itemCenterX = location[0] + v.getWidth() / 2;

            //★两边的图片缩放比例
            float scale = 0.8f;
            //★某个item中心X坐标距recyclerView中心X坐标的偏移量
            int offX = Math.abs(itemCenterX - recyclerViewCenterX);
            //★在一个item的宽度范围内，item从1缩放至scale，那么改变了（1-scale），
            //从下列公式算出随着offX变化，item的变化缩放百分比
            float percent = offX * (1 - scale) / v.getWidth();
            //★取反哟
            float interpretateScale = 1 - percent;
            //这个if不走的话，得到的是多级渐变模式
            if (interpretateScale < scale) {
                interpretateScale = scale;
            }
            v.setScaleX((interpretateScale));
            v.setScaleY((interpretateScale));
        }
    }


}
