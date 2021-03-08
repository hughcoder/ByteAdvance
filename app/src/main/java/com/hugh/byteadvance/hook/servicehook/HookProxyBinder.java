package com.hugh.byteadvance.hook.servicehook;

import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chenyw on 2021/2/5.
 */
public class HookProxyBinder implements InvocationHandler {

    public static final String TAG = HookProxyBinder.class.getSimpleName();
    Object binder;
    public HookProxyBinder(Object binder){
        this.binder = binder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("HookProxyBinder==", method.getName());
        if ("startService".equals(method.getName())) { //拦截启动服务
            int i = 0;
            Intent intent = null;
            for (int index = 0; index < args.length; index++) {
                if (args[index] instanceof Intent) {
                    i = index;
                    intent = (Intent) args[index];
                    break;
                }
            }
            String packageName = intent.getComponent().getPackageName();
            String className = intent.getComponent().getClassName();
//            if (className.equals(MyService.class.getCanonicalName())) {
//                intent.setClassName(packageName, ProxyService.class.getCanonicalName());
//                intent.putExtra(HookProxyBinder, className);
//            }
            args[i] = intent;
        }
        return method.invoke(binder, args);
    }


}
