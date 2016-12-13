package com.allen.androidcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.androidcustomview.bean.CircleBean;
import com.allen.androidcustomview.bean.Point;
import com.allen.androidcustomview.utils.DisplayUtils;
import com.allen.androidcustomview.widget.BubbleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "allen";
    private ImageView hxbIv;
    private TextView hxbTv;
    private Button button;
    private BubbleView bezierView;

    private List<CircleBean> circleBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hxbIv = (ImageView) findViewById(R.id.hxb_iv);
        hxbTv = (TextView) findViewById(R.id.center_tv);
        bezierView = (BubbleView) findViewById(R.id.circle_view);
        button = (Button) findViewById(R.id.start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezierView.setCenterImg(hxbTv);
                bezierView.openAnimation();
            }
        });


        initPoint();

        bezierView.setCircleBeen(circleBeanList);
//        bezierView.createInAnimation();
    }

    private void initPoint() {

        int height = DisplayUtils.getDisplayHight(this);
        int width = DisplayUtils.getDisplayWidth(this);

        int centerX = width / 2;
        int centerY = height / 2;

        Log.d(TAG, "initPoint: " + centerX + "----" + centerY);


        CircleBean circleBean = new CircleBean(
                new Point(-width*5/21, height * 10/33),
                new Point(centerX - 30, height * 2 / 3),
                new Point(centerX - 30, centerY - 120),
                new Point(width / 6, centerY - 120),
                new Point(width / 6, 0),
                84, 60);
        CircleBean circleBean2 = new CircleBean(
                new Point(-width / 4, height * 4 / 6),
                new Point(centerX - 20, height * 3 / 5),
                new Point(centerX - 20, centerY - 10),
                new Point(width / 3, centerY - 10),
                new Point(width / 3, -height * 5 / 27),
                250, 60);
        CircleBean circleBean3 = new CircleBean(
                new Point(-width / 12, height * 5 / 7),
                new Point(centerX - 100, height * 2 / 3),
                new Point(centerX - 100, centerY + 100),
                new Point(0, centerY + 100),
                new Point(0, 0),
                48, 60);

        CircleBean circleBean4 = new CircleBean(
                new Point(-width / 12, height * 4 / 5),
                new Point(centerX, height * 3 / 4),
                new Point(centerX, centerY),
                new Point(width / 2, centerY),
                new Point(width / 2, -height / 7),
                250, 60);

        CircleBean circleBean5 = new CircleBean(
                new Point(width * 5 / 7, height * 4 / 5),
                new Point(centerX, height * 3 / 4),
                new Point(centerX, centerY - 20),
                new Point(width * 10 / 13, centerY - 20),
                new Point(width * 10 / 13, -height * 2 / 13),
                250, 60);
        CircleBean circleBean6 = new CircleBean(
                new Point(width + width / 5, height * 3 / 4),
                new Point(centerX + 20, height * 2 / 3),
                new Point(centerX + 20, centerY + 10),
                new Point(width * 11 / 14, centerY + 10),

                new Point(width * 11 / 14, -height * 5 / 27),
                250, 60);
        CircleBean circleBean7 = new CircleBean(
                new Point(width + width / 7, height * 5 / 8),
                new Point(centerX + 20, height * 4 / 7),
                new Point(centerX + 20, centerY + 10),
                new Point(width, centerY + 10),

                new Point(width, 0),
                120, 60);

        circleBeanList.add(circleBean);
        circleBeanList.add(circleBean2);
        circleBeanList.add(circleBean3);
        circleBeanList.add(circleBean4);
        circleBeanList.add(circleBean5);
        circleBeanList.add(circleBean6);
        circleBeanList.add(circleBean7);




//        CircleBean circleBean = new CircleBean(
//                new Point(-width*5/21, height * 10/33),
//                new Point(centerX - 30, height * 3 / 5),
//                new Point(width*2/5, height/3),
//                new Point(width / 6, centerY - 120),
//                new Point(width / 6, 0),
//                140, 60);
//        CircleBean circleBean2 = new CircleBean(
//                new Point(-width / 4, height * 4 / 6),
//                new Point(centerX - 20, height * 4 / 6),
//                new Point(centerX - 20, centerY - 10),
//                new Point(width / 3, centerY - 10),
//                new Point(width / 3, -height * 5 / 27),
//                300, 60);
//        CircleBean circleBean3 = new CircleBean(
//                new Point(-width / 12, height * 5 / 7),
//                new Point(centerX - 100, height * 5 / 7),
//                new Point(centerX - 100, centerY + 100),
//                new Point(0, centerY + 100),
//                new Point(0, 0),
//                300, 60);
//
//        CircleBean circleBean4 = new CircleBean(
//                new Point(-width / 12, height * 4 / 5),
//                new Point(centerX, height * 4 / 5),
//                new Point(centerX, centerY),
//                new Point(width / 2, centerY),
//                new Point(width / 2, -height / 7),
//                300, 60);
//
//        CircleBean circleBean5 = new CircleBean(
//                new Point(width * 5 / 7, height * 4 / 5),
//                new Point(centerX, height * 4 / 5),
//                new Point(centerX, centerY - 20),
//                new Point(width * 10 / 13, centerY - 20),
//                new Point(width * 10 / 13, -height * 2 / 13),
//                300, 60);
//        CircleBean circleBean6 = new CircleBean(
//                new Point(width + width / 5, height * 3 / 4),
//                new Point(centerX + 20, height * 3 / 4),
//                new Point(centerX + 20, centerY + 10),
//                new Point(width * 11 / 14, centerY + 10),
//
//                new Point(width * 11 / 14, -height * 5 / 27),
//                300, 60);
//        CircleBean circleBean7 = new CircleBean(
//                new Point(width + width / 7, height * 5 / 8),
//                new Point(centerX + 20, height * 5 / 8),
//                new Point(centerX + 20, centerY + 10),
//                new Point(width, centerY + 10),
//
//                new Point(width, 0),
//                180, 60);

    }
}
