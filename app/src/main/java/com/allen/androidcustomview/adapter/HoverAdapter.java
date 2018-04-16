package com.allen.androidcustomview.adapter;

import android.support.annotation.Nullable;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.bean.UserBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/04/16
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class HoverAdapter extends BaseQuickAdapter<UserBean,BaseViewHolder> {

    public HoverAdapter( @Nullable List<UserBean> data) {
        super(R.layout.adapter_item_hover_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.user_name_tv,item.getUserName());
    }

}
