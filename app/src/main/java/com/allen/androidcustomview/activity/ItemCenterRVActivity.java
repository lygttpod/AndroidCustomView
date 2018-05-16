package com.allen.androidcustomview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.adapter.ItemCenter2Adapter;
import com.allen.androidcustomview.adapter.ItemCenterAdapter;
import com.allen.androidcustomview.widget.ItemCenterRecyclerView;

import java.util.ArrayList;
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

public class ItemCenterRVActivity extends AppCompatActivity {
    private ItemCenterRecyclerView recyclerView;
    private ItemCenterRecyclerView recyclerView2;
    private ItemCenterAdapter adapter;
    private ItemCenter2Adapter adapter2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_center);


        recyclerView = (ItemCenterRecyclerView) findViewById(R.id.recycler_view);
        recyclerView2 = (ItemCenterRecyclerView) findViewById(R.id.recycler_view_2);

        initRv(recyclerView);
        initRv(recyclerView2);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);

//        recyclerView2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
        setSyncScroll(recyclerView,recyclerView2);

    }

    private void initRv(ItemCenterRecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);

        adapter = new ItemCenterAdapter(this,recyclerView,getData());
        adapter2 = new ItemCenter2Adapter(this,recyclerView,getData());
    }

    private List<String> getData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("");
        }
        return strings;
    }

    public  void setSyncScroll(final RecyclerView leftList, final RecyclerView rightList) {
        leftList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    rightList.scrollBy(dx, dy);
                }
            }
        });

        rightList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    leftList.scrollBy(dx, dy);
                }
            }
        });
    }
}
