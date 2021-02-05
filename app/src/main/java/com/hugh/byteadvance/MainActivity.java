package com.hugh.byteadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.hugh.byteadvance.annotation.AnnoActivity;
import com.hugh.byteadvance.aop.AopActivity;
import com.hugh.byteadvance.binder.ClientActivity;
import com.hugh.byteadvance.dragvideo.DragVideoViewActivity;
import com.hugh.byteadvance.dragvideo.videopage.VideoViewActivity;
import com.hugh.byteadvance.hook.EvilInstrumentation;
import com.hugh.byteadvance.hook.HookActivity;
import com.hugh.byteadvance.jetpack.databing.DataBindingActivity;
import com.hugh.byteadvance.jetpack.viewmodel.ViewModelActivity;
import com.hugh.byteadvance.jetpack.viewmodel.fragment.MainFragActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        mBtnAop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("aaa","hhaahahah");
                return true;
            }
        });
        findViewById(R.id.go_to_aidl).setOnClickListener(this);
        findViewById(R.id.go_to_dataBinding).setOnClickListener(this);
        findViewById(R.id.go_to_view_model).setOnClickListener(this);
        findViewById(R.id.go_to_fragment).setOnClickListener(this);
        findViewById(R.id.go_to_pic_pic).setOnClickListener(this);
        findViewById(R.id.go_to_drag_video).setOnClickListener(this);
        findViewById(R.id.go_to_anno).setOnClickListener(this);
        findViewById(R.id.go_to_hook_edittext).setOnClickListener(this);
        try {
            hookStartActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookStartActivity() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);


        // 拿到原始的 mInstrumentation字段
        //java.lang.reflect.Field 为我们提供了获取当前对象的成员变量的类型，和重新设值的方法。
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        //通过get获取到 Instrumentation对象
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);

    }

    @SuppressLint("NonConstantResourceId")
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
            case R.id.go_to_drag_video:
                startActivity(new Intent(mActivity, VideoViewActivity.class));
                break;
            case R.id.go_to_anno:
                getApplicationContext().startActivity(new Intent(mActivity, AnnoActivity.class));
//                startActivity(new Intent(mActivity, AnnoActivity.class));
                break;
            case R.id.go_to_hook_edittext:
                getApplicationContext().startActivity(new Intent(mActivity, HookActivity.class));
//                startActivity(new Intent(mActivity, AnnoActivity.class));
                break;
        }
    }


}
