package com.allen.androidcustomview.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.utils.DisplayUtils;
import com.allen.androidcustomview.widget.ItemCenterRecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/05/07
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class ItemCenterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private ItemCenterRecyclerView recyclerView;
    private ViewGroup.MarginLayoutParams p;
    private int mRecyclerViewWidth;
    private List<String> data;

    public ItemCenterAdapter(Context context, ItemCenterRecyclerView recyclerView, @Nullable List<String> data) {
        super(R.layout.adapter_item_center, data);
        mRecyclerViewWidth = DisplayUtils.getDisplayWidth(context);
        this.data = data;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        View itemRoot = helper.getView(R.id.item_root);
        p = (ViewGroup.MarginLayoutParams) itemRoot.getLayoutParams();
        // 为了居中， 第一个条目leftMagrin、最后一个条目的rightMargin是（recyclerView宽度减去一个条目的宽度）/2
        int margin = (mRecyclerViewWidth - p.width) / 2;
        if (helper.getLayoutPosition() == 0) {
            p.leftMargin = margin;
            p.rightMargin = 0;
            itemRoot.setLayoutParams(p);
        } else if (helper.getLayoutPosition() == data.size() - 1) {
            p.leftMargin = 0;
            p.rightMargin = margin;
            itemRoot.setLayoutParams(p);
        } else {
            p.leftMargin = 0;
            p.rightMargin = 0;
            itemRoot.setLayoutParams(p);
        }

        itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickToCenter(v);
            }
        });
    }

    private void setClickToCenter(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int currentX = location[0];
        int currentCenterX = (int) (currentX + p.width / 2 * 0.8f);//因为除了中间外的其他条目是被缩放为0.8的状态
        int recyclerViewCenterX = mRecyclerViewWidth / 2;
        int offX = currentCenterX - recyclerViewCenterX;

        if (Math.abs(offX) > p.width / 2 * 0.21f) {//因为已经居中的Item，已经被放大到比例1了
            recyclerView.smoothScrollBy(offX, 0);
        }
    }
}
