package com.hugh.byteadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hugh.byteadvance.aop.AopActivity;
import com.hugh.byteadvance.binder.ClientActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity mActivity;
    private Button mBtnAop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        mBtnAop = findViewById(R.id.go_to_aop);
        mBtnAop.setOnClickListener(this);
        findViewById(R.id.go_to_aidl).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_aop:
                startActivity(new Intent(mActivity, AopActivity.class));
                break;
            case R.id.go_to_aidl:
                startActivity(new Intent(mActivity, ClientActivity.class));
                break;
        }
    }




}
