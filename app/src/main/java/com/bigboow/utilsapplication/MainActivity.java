package com.bigboow.utilsapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bigboow.utilsapplication.encrypt.AES.AESTest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn1 = findViewById(R.id.button_id_1);
        btn2 = findViewById(R.id.button_id_2);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_id_1:
                LogUtil.d("button_id_1");
                AESTest.encryptTest();
                break;
            case R.id.button_id_2:
                LogUtil.d("button_id_2");
                AESTest.decryptTest();
                break;
            default:
                break;
        }


    }
}