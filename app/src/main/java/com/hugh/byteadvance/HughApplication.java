package com.hugh.byteadvance;

import android.app.Application;
import android.util.Log;

import com.hugh.byteadvance.hook.servicehook.ServiceHook;

/**
 * Created by chenyw on 2021/2/5.
 */
public class HughApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.e("aaa","开始hook");
            ServiceHook.hook(HughApplication.this);
            Log.e("aaa","hook成功");
        } catch (ClassNotFoundException e) {
            Log.e("aaa","hook失败1:"+e.toString());
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Log.e("aaa","hook失败2:"+e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e("aaa","hook失败3:"+e.toString());
            e.printStackTrace();
        }
    }

}
