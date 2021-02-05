package com.hugh.byteadvance.hook.wifihook;

/**
 * Created by chenyw on 2021/1/8.
 */

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/7/18 8:00 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class WifiHook {


    private static Class iWifiManager;

    private static Field serviceField;

    private volatile static boolean isHook;

    public static void hook(Context context) {
        try {
            if (isHook) {
                return;
            }
            iWifiManager = Class.forName("android.net.wifi.IWifiManager");
            serviceField = WifiManager.class.getDeclaredField("mService");
            serviceField.setAccessible(true);

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Object realIwm = serviceField.get(wifi);
            serviceField.set(wifi, Proxy.newProxyInstance(iWifiManager.getClassLoader(),
                    new Class[]{iWifiManager},
                    new IWMInvocationHandler(context, realIwm)));
            Log.e("AAA", "hook success");
            isHook = true;
            wifi.setWifiEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class IWMInvocationHandler implements InvocationHandler {

        private Object real;

        private Context context;


        public IWMInvocationHandler(Context context, Object real) {
            this.context = context;
            this.real = real;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("aaa", "method invoke " + method.getName() + ",args:" + Arrays.toString(args));
            if ("setWifiEnabled".equals(method.getName())) {
                try {
//                    String networkOperator = SystemUtils.getNetworkOperator(context);
//                    String networkOperatorName = SystemUtils.getNetworkOperatorName(context);
//                    String simCountryIso = SystemUtils.getSimCountryIso(context);
//                    String simOperator = SystemUtils.getSimOperator(context);
//                    String simOperatorName = SystemUtils.getSimOperatorName(context);
//                    String networkType = SystemUtils.getNetworkType(context);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return method.invoke(real, args);
        }
    }
}
