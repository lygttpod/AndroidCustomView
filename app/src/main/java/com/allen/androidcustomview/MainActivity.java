package com.allen.androidcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.allen.androidcustomview.widget.WaveViewByBezier;


public class MainActivity extends AppCompatActivity {

    private Button button_bubble;
    private Button button_wave_bezier;
    private Button button_wave_sin_cos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_bubble = (Button) findViewById(R.id.bubble_view_btn);
        button_wave_bezier = (Button) findViewById(R.id.wave_view_by_bezier_btn);
        button_wave_sin_cos = (Button) findViewById(R.id.wave_view_by_sin_cos_btn);

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

    }

}
