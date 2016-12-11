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
                bezierView.createInAnimation();
            }
        });

        initPoint();

        bezierView.setCircleBeen(circleBeanList);
//        bezierView.createInAnimation();
    }

    private void initPoint() {

        int hight = DisplayUtils.getDisplayHight(this);
        int width = DisplayUtils.getDisplayWidth(this);

        int centerX = width / 2;
        int centerY = hight / 3;

        Log.d(TAG, "initPoint: " + centerX + "----" + centerY);

        CircleBean circleBean = new CircleBean(
                new Point(-width / 4, hight * 3 / 5),
                new Point(centerX - 30, hight * 3 / 5),
                new Point(centerX - 30, centerY - 120),
                new Point(width / 6, centerY - 120),
                new Point(width / 6, 0),
                100, 60);
        CircleBean circleBean2 = new CircleBean(
                new Point(-width / 4, hight * 4 / 6),
                new Point(centerX - 20, hight * 4 / 6),
                new Point(centerX - 20, centerY - 10),
                new Point(width / 3, centerY - 10),
                new Point(width / 3, -hight * 5 / 27),
                130, 60);
        CircleBean circleBean3 = new CircleBean(
                new Point(-width / 12, hight * 5 / 7),
                new Point(centerX - 100, hight * 5 / 7),
                new Point(centerX - 100, centerY + 100),
                new Point(0, centerY + 100),
                new Point(0, 0),
                80, 60);

        CircleBean circleBean4 = new CircleBean(
                new Point(-width / 12, hight * 4 / 5),
                new Point(centerX, hight * 4 / 5),
                new Point(centerX, centerY),
                new Point(width / 2, centerY),
                new Point(width / 2, -hight / 7),
                200, 60);

        CircleBean circleBean5 = new CircleBean(
                new Point(width * 5 / 7, hight * 4 / 5),
                new Point(centerX, hight * 4 / 5),
                new Point(centerX, centerY - 20),
                new Point(width * 10 / 13, centerY - 20),
                new Point(width * 10 / 13, -hight * 2 / 13),
                260, 60);
        CircleBean circleBean6 = new CircleBean(
                new Point(width + width / 5, hight * 3 / 4),
                new Point(centerX + 20, hight * 3 / 4),
                new Point(centerX + 20, centerY + 10),
                new Point(width * 11 / 14, centerY + 10),

                new Point(width * 11 / 14, -hight * 5 / 27),
                70, 60);
        CircleBean circleBean7 = new CircleBean(
                new Point(width + width / 7, hight * 5 / 8),
                new Point(centerX + 20, hight * 5 / 8),
                new Point(centerX + 20, centerY + 10),
                new Point(width, centerY + 10),

                new Point(width, 0),
                100, 60);

        circleBeanList.add(circleBean);
        circleBeanList.add(circleBean2);
        circleBeanList.add(circleBean3);
        circleBeanList.add(circleBean4);
        circleBeanList.add(circleBean5);
        circleBeanList.add(circleBean6);
        circleBeanList.add(circleBean7);
    }
}
