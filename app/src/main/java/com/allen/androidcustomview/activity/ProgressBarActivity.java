package com.allen.androidcustomview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.widget.CircleProgressBarView;
import com.allen.androidcustomview.widget.HorizontalProgressBar;

public class ProgressBarActivity extends AppCompatActivity {

    CircleProgressBarView circleProgressBarView;
    HorizontalProgressBar horizontalProgressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregress_bar);

        circleProgressBarView = (CircleProgressBarView) findViewById(R.id.circle_progress_view);

        horizontalProgressBar = (HorizontalProgressBar) findViewById(R.id.horizontal_progress_view);

        textView = (TextView) findViewById(R.id.progress_tv);

        circleProgressBarView.setProgress(70);
        circleProgressBarView.setProgressListener(new CircleProgressBarView.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
                textView.setText("当前进度：" + currentProgress);
            }
        });
        circleProgressBarView.startProgressAnimation();

        horizontalProgressBar.setProgress(98).setProgressListener(new HorizontalProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
            }
        });
        horizontalProgressBar.startProgressAnimation();

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
