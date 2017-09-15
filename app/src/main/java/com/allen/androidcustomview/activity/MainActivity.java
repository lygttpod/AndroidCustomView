package com.allen.androidcustomview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.tagview.TagActivity;


public class MainActivity extends AppCompatActivity {

    private Button button_bubble;
    private Button button_wave_bezier;
    private Button button_wave_sin_cos;
    private Button button_radar;
    private Button button_tag;
    private Button animation_btn;
    private Button pay_psd_view_btn;
    private Button progress_btn;

    private Button animationViewBtn;
    private Button huaweiViewBtn;
    private Button fingerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_bubble = (Button) findViewById(R.id.bubble_view_btn);
        button_wave_bezier = (Button) findViewById(R.id.wave_view_by_bezier_btn);
        button_wave_sin_cos = (Button) findViewById(R.id.wave_view_by_sin_cos_btn);
        button_radar = (Button) findViewById(R.id.radar_view_btn);
        button_tag = (Button) findViewById(R.id.tag_view_btn);
        animation_btn = (Button) findViewById(R.id.animation_btn);
        pay_psd_view_btn = (Button) findViewById(R.id.pay_psd_view_btn);
        progress_btn = (Button) findViewById(R.id.progress_btn);
        animationViewBtn = (Button) findViewById(R.id.animation_view_btn);
        huaweiViewBtn = (Button) findViewById(R.id.huawei_view_btn);
        fingerBtn = (Button) findViewById(R.id.finger_btn);

        button_bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BubbleViewActivity.class));
            }
        });
        button_wave_bezier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WaveByBezierActivity.class));
            }
        });
        button_wave_sin_cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WaveBySinCosActivity.class));
            }
        });
        button_radar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RadarActivity.class));
            }
        });
        button_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TagActivity.class));
            }
        });
        animation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AnimationBtnActivity.class));
            }
        });

        pay_psd_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PayPsdViewActivity.class));
            }
        });
        progress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProgressBarActivity.class));
            }
        });
        animationViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimationViewActivity.class));
            }
        });
        huaweiViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DragBallActivity.class));
            }
        });
        fingerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FingerprintActivity.class));
            }
        });


    }

}
