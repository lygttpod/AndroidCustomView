package com.allen.androidcustomview.tagview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.allen.androidcustomview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/4/14.
 *
 * 评论页面的适配器
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<TagBean> tagList;

    private boolean isSelected = false;

    private List<TagBean> selectList;

    public TagAdapter(List<TagBean> tagList) {
        this.tagList = tagList;
        selectList = new ArrayList<>();
    }

    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final TagAdapter.ViewHolder holder, final int position) {
        holder.mTextView.setText(tagList.get(position).getTag_name());
        holder.itemView.setTag(tagList.get(position));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = !holder.mTextView.isSelected();
                if (isSelected) {
                    holder.mTextView.setSelected(true);
                    holder.mTextView.setBackgroundResource(R.drawable.tag_checked_bg);
                    selectList.add(tagList.get(position));
                } else {
                    holder.mTextView.setSelected(false);
                    holder.mTextView.setBackgroundResource(R.drawable.tag_normal_bg);
                    selectList.remove(tagList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tag_tv);
        }
    }

    public List<TagBean> getSelectData(){
        return selectList;
    }
}
