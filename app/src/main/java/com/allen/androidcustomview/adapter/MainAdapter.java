package com.allen.androidcustomview.adapter;

import android.support.annotation.Nullable;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.bean.TypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/05/14
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class MainAdapter extends BaseQuickAdapter<TypeBean,BaseViewHolder> {

    public MainAdapter( @Nullable List<TypeBean> data) {
        super(R.layout.adapter_item_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TypeBean item) {
        helper.setText(R.id.title_tv,item.getTitle());
    }
}
