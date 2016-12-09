package com.allen.androidcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.allen.androidcustomview.bean.CircleBean;
import com.allen.androidcustomview.bean.Point;
import com.allen.androidcustomview.utils.DisplayUtils;
import com.allen.androidcustomview.widget.BezierView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "allen";
    private Button button;
    private BezierView bezierView;

    private List<CircleBean> circleBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bezierView = (BezierView) findViewById(R.id.circle_view);
        button = (Button) findViewById(R.id.start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezierView.createAnimation();
            }
        });

        initPoint();

        bezierView.setCircleBeen(circleBeanList);
        bezierView.createAnimation();
    }

    private void initPoint() {

        int hight = DisplayUtils.getDisplayHight(this);
        int width = DisplayUtils.getDisplayWidth(this);

        int centerX = width / 2;
        int centerY = hight / 3;

        Log.d(TAG, "initPoint: " + centerX + "----" + centerY);

        CircleBean circleBean = new CircleBean(new Point(-180, 900), new Point(700, 900), new Point(centerX - 30, centerY - 120), 42, 60);
        CircleBean circleBean2 = new CircleBean(new Point(-180, 1114), new Point(1200, 1000), new Point(centerX - 20, centerY - 10), 90, 60);
        CircleBean circleBean3 = new CircleBean(new Point(-60, 1234), new Point(1000, 900), new Point(centerX - 100, centerY + 100), 24, 60);

        CircleBean circleBean4 = new CircleBean(new Point(530, 1454), new Point(1000, 900), new Point(centerX, centerY), 120, 60);

        CircleBean circleBean5 = new CircleBean(new Point(870, 1294), new Point(1000, 900), new Point(centerX, centerY - 20), 150, 60);
        CircleBean circleBean6 = new CircleBean(new Point(824, 1080), new Point(1000, 900), new Point(centerX + 20, centerY + 10), 54, 60);

        circleBeanList.add(circleBean);
        circleBeanList.add(circleBean2);
        circleBeanList.add(circleBean3);
        circleBeanList.add(circleBean4);
        circleBeanList.add(circleBean5);
        circleBeanList.add(circleBean6);
    }
}
