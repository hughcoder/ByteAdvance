package com.hugh.byteadvance.aop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hugh.byteadvance.R;

import androidx.annotation.Nullable;

/**
 * Created by chenyw on 2020/8/12.
 */
public class AopActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop);

        AopEntity.innerB innerB = new AopEntity.innerB();
        innerB.setName("qqqqqqq");
        Log.e("aaa", "getName-----" + innerB.getName());
    }
}
