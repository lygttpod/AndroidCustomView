package com.allen.androidcustomview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.widget.PayPsdInputView;

public class PayPsdViewActivity extends AppCompatActivity {
    private PayPsdInputView passwordInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_psd_view);

        passwordInputView = (PayPsdInputView) findViewById(R.id.password);

        passwordInputView.setComparePassword( new PayPsdInputView.onPasswordListener() {

            @Override
            public void onDifference(String oldPsd, String newPsd) {
                // TODO: 2018/1/22  和上次输入的密码不一致  做相应的业务逻辑处理
                Toast.makeText(PayPsdViewActivity.this, "两次密码输入不同" + oldPsd + "!=" + newPsd, Toast.LENGTH_SHORT).show();
                passwordInputView.cleanPsd();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                Toast.makeText(PayPsdViewActivity.this, "密码相同" + psd, Toast.LENGTH_SHORT).show();
                passwordInputView.setComparePassword("");
                passwordInputView.cleanPsd();
            }

            @Override
            public void inputFinished(String inputPsd) {
                // TODO: 2018/1/3 输完逻辑
                Toast.makeText(PayPsdViewActivity.this, "输入完毕：" + inputPsd, Toast.LENGTH_SHORT).show();
                passwordInputView.setComparePassword(inputPsd);
            }
        });

    }
}
