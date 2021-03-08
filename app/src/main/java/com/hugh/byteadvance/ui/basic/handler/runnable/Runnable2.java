package com.hugh.byteadvance.ui.basic.handler.runnable;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import static com.hugh.byteadvance.ui.basic.handler.HandlerActivity.MAIN_2_SUB2;

/**
 * Created by chenyw on 2021/3/7.
 */
public class Runnable2 implements Runnable {
    public Looper mylooper2;
    public Handler handler2;

    @Override
    public void run() {
        Looper.prepare();              //创建一个新的looper，并将其放入sThreadLocal中
        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MAIN_2_SUB2) {
                    Log.e("aaa", "I am from main thread");
                    Log.e("aaa", "thread=" + Thread.currentThread().getName());
                }
            }
        };

        mylooper2 = Looper.myLooper();//从threadlocal中取出当前线程的looper
        Looper.loop();
    }
}
