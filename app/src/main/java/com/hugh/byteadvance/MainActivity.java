package com.hugh.byteadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hugh.byteadvance.aop.AopActivity;
import com.hugh.byteadvance.binder.ClientActivity;
import com.hugh.byteadvance.dragvideo.DragVideoViewActivity;
import com.hugh.byteadvance.jetpack.databing.DataBindingActivity;
import com.hugh.byteadvance.jetpack.viewmodel.ViewModelActivity;
import com.hugh.byteadvance.jetpack.viewmodel.fragment.MainFragActivity;

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
        findViewById(R.id.go_to_dataBinding).setOnClickListener(this);
        findViewById(R.id.go_to_view_model).setOnClickListener(this);
        findViewById(R.id.go_to_fragment).setOnClickListener(this);
        findViewById(R.id.go_to_pic_pic).setOnClickListener(this);
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
            case R.id.go_to_dataBinding:
                startActivity(new Intent(mActivity, DataBindingActivity.class));
                break;
            case R.id.go_to_view_model:
                startActivity(new Intent(mActivity, ViewModelActivity.class));
                break;
            case R.id.go_to_fragment:
                startActivity(new Intent(mActivity, MainFragActivity.class));
                break;
            case R.id.go_to_pic_pic:
                startActivity(new Intent(mActivity, DragVideoViewActivity.class));
                break;
        }
    }


}
