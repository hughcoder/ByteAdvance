package com.hugh.byteadvance.hook.servicehook;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.hugh.byteadvance.hook.wifihook.WifiHook;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by chenyw on 2021/2/5.
 */
public class ServiceHook {

    public static void hook(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //1、反射获取ActivityManager类中的静态实例IActivityManagerSingleton
        Class<?> manager = Class.forName("android.app.ActivityManager");
        Field field = manager.getDeclaredField("IActivityManagerSingleton");
        field.setAccessible(true);
        Object object = field.get(null);
//反射获取Singleton中的mInstance实例，mInstance就是调用create之后创建的对象，此处就是IActivityManager的代理实例
        Class<?> singlen = Class.forName("android.util.Singleton");
        Field field1 = singlen.getDeclaredField("mInstance");
        field1.setAccessible(true);
        Object binder = field1.get(object); //2、获取此IActivityManagerSingleton内部的mInstance
        Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
//3、创建代理IActivityManager
        Object binder1 = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManagerInterface}, new HookProxyBinder(binder));
//4、将重写的代理IActivityManager设置给mInstance
        field1.set(object, binder1);
    }
}
