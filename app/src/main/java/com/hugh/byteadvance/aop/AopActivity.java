package com.hugh.byteadvance.aop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.aop.pointcut.CustomPointCut;

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
        innerB.setAopName("qqqqqqq");
        Log.e("aaa", "getName-----" + innerB.getAopName());

        customMethod();
    }

    @CustomPointCut(permissionName = {"PHONE", "STATUS"})
    public void customMethod(){
        Toast.makeText(this,"customMethod call",Toast.LENGTH_LONG).show();
    }

}
