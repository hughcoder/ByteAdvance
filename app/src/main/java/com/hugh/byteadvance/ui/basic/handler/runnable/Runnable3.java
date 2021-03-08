package com.hugh.byteadvance.ui.basic.handler.runnable;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import static com.hugh.byteadvance.ui.basic.handler.HandlerActivity.SUB_2_SUB;

/**
 * Created by chenyw on 2021/3/7.
 */
public class Runnable3 implements Runnable {

    public Handler handler3;

    public Looper myLooper3;

    @Override
    public void run() {
        Looper.prepare();
        handler3 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == SUB_2_SUB) {
                    Log.e("aaa", "I am from other thread");
                    Log.e("aaa", "thread=" + Thread.currentThread().getName());
                }
            }
        };

        myLooper3 = Looper.myLooper();

        Looper.loop();

    }
}
