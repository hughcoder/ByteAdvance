package com.hugh.byteadvance.hook.wifimanager;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chenyw on 2021/1/8.
 */
public class WifiBinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    // 原始的Service对象 (IInterface)
    Object base;

    public WifiBinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            // IClipboard.Stub.asInterface(base);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("wifi hooked failed!");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 把剪切版的内容替换为 "you are hooked"
        if ("getConnectionInfo".equals(method.getName())) {
            Log.d(TAG, "hook getConnectionInfo");
//            return ClipData.newPlainText(null, "you are hooked");
        }

        // 欺骗系统,使之认为剪切版上一直有内容
//        if ("hasPrimaryClip".equals(method.getName())) {
//            return true;
//        }

        return method.invoke(base, args);
    }
}