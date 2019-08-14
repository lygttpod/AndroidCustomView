package com.allen.androidcustomview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.widget.CircleProgressBarView;
import com.allen.androidcustomview.widget.HorizontalProgressBar;
import com.allen.androidcustomview.widget.LoadingLineView;
import com.allen.androidcustomview.widget.LoadingView;
import com.allen.androidcustomview.widget.ProductProgressBar;
import com.allen.androidcustomview.widget.StudyPlanProgressView;

import java.util.ArrayList;
import java.util.List;

public class ProgressBarActivity extends AppCompatActivity {

    CircleProgressBarView circleProgressBarView;
    HorizontalProgressBar horizontalProgressBar;
    ProductProgressBar productProgressBar;
    LoadingView loadingView;
    TextView textView;
    LoadingLineView loadingLineView;

    Button button;
    StudyPlanProgressView studyPlanProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregress_bar);

        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circle_progress_view);

        horizontalProgressBar = (HorizontalProgressBar) findViewById(R.id.horizontal_progress_view);
        productProgressBar = (ProductProgressBar) findViewById(R.id.product_progress_view);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
        loadingLineView = (LoadingLineView) findViewById(R.id.loading_line_view);

        textView = (TextView) findViewById(R.id.progress_tv);
        button = (Button) findViewById(R.id.startAnimationBtn);
        studyPlanProgressView = findViewById(R.id.study_plan_progress_view);

        circleProgressBarView.setProgressWithAnimation(60);
        circleProgressBarView.setProgressListener(new CircleProgressBarView.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
                textView.setText("当前进度：" + currentProgress);
            }
        });
        circleProgressBarView.startProgressAnimation();

        horizontalProgressBar.setProgressWithAnimation(60).setProgressListener(new HorizontalProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
            }
        });
        horizontalProgressBar.startProgressAnimation();

        productProgressBar.setProgress(60).setProgressListener(new ProductProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
                Log.e("allen", "currentProgressListener: " + currentProgress);
            }
        });

        loadingView.startAnimation();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLineView.startLoading();
                loadingView.startAnimation();
                horizontalProgressBar.setProgressWithAnimation(100);
                productProgressBar.setProgress(100);
                circleProgressBarView.setProgressWithAnimation(60).startProgressAnimation();
                circleProgressBarView.setProgressListener(new CircleProgressBarView.ProgressListener() {
                    @Override
                    public void currentProgressListener(float currentProgress) {
                        textView.setText("当前进度：" + currentProgress);
                    }
                });
                studyPlanProgressView.setData(getPlanData(true));
            }
        });
        studyPlanProgressView.setData(getPlanData(false));

    }

    private List<String> getPlanData(Boolean isAll) {
        List<String> list = new ArrayList<>();
        list.add("08月10日");
        list.add("08月11日");
        list.add("08月12日");
        list.add("08月13日");
        if (isAll) {
            list.add("08月14日");
            list.add("08月15日");
            list.add("08月16日");
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        circleProgressBarView.resumeProgressAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        circleProgressBarView.pauseProgressAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        circleProgressBarView.stopProgressAnimation();
    }
}
