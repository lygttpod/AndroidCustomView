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
                new Point((float) (-width/5.1), (float) (height/1.5)),
                new Point(centerX - 30, height * 2 / 3),
                new Point((float) (width/2.4), (float) (height/3.4)),
                new Point(width / 6, centerY - 120),
                new Point((float) (width / 7.2), -height/128),
                (float) (width/14.4), 60);
        CircleBean circleBean2 = new CircleBean(
                new Point(-width / 4, (float) (height /1.3)),
                new Point(centerX - 20, height * 3 / 5),
                new Point((float) (width/2.1), (float) (height/2.5)),
                new Point(width / 3, centerY - 10),
                new Point(width/4, (float) (-height/5.3)),
                width/4, 60);
        CircleBean circleBean3 = new CircleBean(
                new Point(-width / 12, (float) (height /1.1)),
                new Point(centerX - 100, height * 2 / 3),
                new Point((float) (width/3.4), height/2),
                new Point(0, centerY + 100),
                new Point(0, 0),
                width/24, 60);

        CircleBean circleBean4 = new CircleBean(
                new Point(-width / 9, (float) (height/0.9)),
                new Point(centerX, height * 3 / 4),
                new Point((float) (width/2.1), (float) (height/2.3)),
                new Point(width / 2, centerY),
                new Point((float) (width / 1.5), (float) (-height /5.6)),
                width/4, 60);

        CircleBean circleBean5 = new CircleBean(
                new Point((float) (width /1.4), (float) (height /0.9)),
                new Point(centerX, height * 3 / 4),
                new Point(width/2, (float) (height/2.37)),
                new Point(width * 10 / 13, centerY - 20),
                new Point(width/2, (float) (-height /7.1)),
                width/4, 60);
        CircleBean circleBean6 = new CircleBean(
                new Point((float) (width /0.8), height ),
                new Point(centerX + 20, height * 2 / 3),
                new Point((float) (width/1.9), (float) (height/2.3)),
                new Point(width * 11 / 14, centerY + 10),

                new Point((float) (width /1.1), (float) (-height /6.4)),
                (float) (width/4), 60);
        CircleBean circleBean7 = new CircleBean(
                new Point((float) (width /0.9), (float) (height/1.2)),
                new Point(centerX + 20, height * 4 / 7),
                new Point((float) (width/1.6), (float) (height/1.9)),
                new Point(width, centerY + 10),

                new Point(width, 0),
                (float) (width/9.6), 60);

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

    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
