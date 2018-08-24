package com.allen.androidcustomview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.adapter.MainAdapter;
import com.allen.androidcustomview.bean.TypeBean;
import com.allen.androidcustomview.tagview.TagActivity;
import com.allen.androidcustomview.widget.SuperDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {


    private RecyclerView recyclerView;

    private MainAdapter adapter;

    private List<TypeBean> typeBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new MainAdapter(getData());
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SuperDividerItemDecoration.Builder(this)
                .setDividerWidth(5)
                .setDividerColor(getResources().getColor(R.color.colorAccent))
                .build());
        recyclerView.setAdapter(adapter);
    }

    private List<TypeBean> getData() {
        typeBeans.add(new TypeBean("气泡漂浮动画", 0));
        typeBeans.add(new TypeBean("波浪动画--贝塞尔曲线实现", 1));
        typeBeans.add(new TypeBean("波浪动画--正余弦函数实现", 2));
        typeBeans.add(new TypeBean("水波（雷达）扩散效果", 3));
        typeBeans.add(new TypeBean("RecyclerView实现另类的Tag标签", 4));
        typeBeans.add(new TypeBean("按钮自定义动画", 5));
        typeBeans.add(new TypeBean("自定义支付密码输入框", 6));
        typeBeans.add(new TypeBean("自定义进度条", 7));
        typeBeans.add(new TypeBean("使用的带动画的view", 8));
        typeBeans.add(new TypeBean("粘性小球", 9));
        typeBeans.add(new TypeBean("banner", 10));
        typeBeans.add(new TypeBean("吸顶效果--一行代码实现", 11));
        return typeBeans;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (typeBeans.get(position).getType()) {
            case 0:
                startActivity(new Intent(MainActivity.this, BubbleViewActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, WaveByBezierActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, WaveBySinCosActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, RadarActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, TagActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, AnimationBtnActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, PayPsdViewActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, ProgressBarActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, AnimationViewActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, DragBallActivity.class));
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
                break;
            case 11:
                startActivity(new Intent(MainActivity.this, HoverItemActivity.class));
                break;
            default:

        }
    }
}
