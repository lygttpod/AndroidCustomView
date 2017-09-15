package com.allen.androidcustomview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.utils.FingerprintUtil;

/**
 * Created by xiaoyao on 2017/9/14.
 */

public class FingerprintActivity extends AppCompatActivity {

    private static final String TAG = "allen";


    TextView resultTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finger_print);

        resultTv = (TextView) findViewById(R.id.result_tv);
        findViewById(R.id.open_finger_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFinger();
            }
        });


        findViewById(R.id.cancel_finger_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerprintUtil.cancel();
            }
        });


    }

    private void openFinger() {

        FingerprintUtil.callFingerPrint(this, new FingerprintUtil.OnCallBackListener() {
            @Override
            public void onSupportFailed() {
                Toast.makeText(FingerprintActivity.this, "当前设备不支持指纹", Toast.LENGTH_LONG).show();
                Log.e(TAG, "当前设备不支持指纹");
            }

            @Override
            public void onInsecurity() {
                Toast.makeText(FingerprintActivity.this, "当前设备未处于安全保护中", Toast.LENGTH_LONG).show();
                Log.e(TAG, "当前设备未处于安全保护中");
            }

            @Override
            public void onEnrollFailed() {
                Toast.makeText(FingerprintActivity.this, "请到设置中设置指纹", Toast.LENGTH_LONG).show();
                Log.e(TAG, "请到设置中设置指纹");
            }

            @Override
            public void onAuthenticationStart() {
                Toast.makeText(FingerprintActivity.this, "验证开始", Toast.LENGTH_LONG).show();

                Log.e(TAG, "onAuthenticationStart: ");
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                Toast.makeText(FingerprintActivity.this, errString, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onAuthenticationError: ");
            }

            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(FingerprintActivity.this, "解锁失败", Toast.LENGTH_LONG).show();

                Log.e(TAG, "解锁失败");
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                Toast.makeText(FingerprintActivity.this, helpString, Toast.LENGTH_LONG).show();

                Log.e(TAG, "onAuthenticationHelp: ");
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                Toast.makeText(FingerprintActivity.this, "解锁成功", Toast.LENGTH_LONG).show();
                Log.e(TAG, "解锁成功");
                resultTv.setText(result.getClass().getName());
            }
        });
    }
}
