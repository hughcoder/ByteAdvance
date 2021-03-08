package com.hugh.byteadvance.ui.basic.handler.runnable;

import android.os.Handler;
import android.os.Message;

import static com.hugh.byteadvance.ui.basic.handler.HandlerActivity.SUB1_2_MAIN;

/**
 * Created by chenyw on 2021/3/7.
 */
public class Runnable1 implements Runnable {

    Handler handler;

    public Runnable1(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.what = SUB1_2_MAIN;
        handler.sendMessage(msg);
    }
}
