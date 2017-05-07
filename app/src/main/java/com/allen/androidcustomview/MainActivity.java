package com.allen.androidcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allen.androidcustomview.tagview.TagActivity;
import com.allen.androidcustomview.widget.PayPsdInputView;


public class MainActivity extends AppCompatActivity {

    private Button button_bubble;
    private Button button_wave_bezier;
    private Button button_wave_sin_cos;
    private Button button_radar;
    private Button button_tag;
    private Button animation_btn;


    private PayPsdInputView passwordInputView;
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

        passwordInputView = (PayPsdInputView) findViewById(R.id.password);

        passwordInputView.setComparePassword("123456", new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference() {
                // TODO: 2017/5/7   和上次输入的密码不一致  做相应的业务逻辑处理
                Toast.makeText(MainActivity.this,"两次密码输入不同",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                Toast.makeText(MainActivity.this,"密码相同"+psd,Toast.LENGTH_SHORT).show();

            }
        });

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

    }

}
