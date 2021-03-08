package com.hugh.byteadvance.ui.basic.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.ui.basic.handler.runnable.Runnable1;
import com.hugh.byteadvance.ui.basic.handler.runnable.Runnable2;
import com.hugh.byteadvance.ui.basic.handler.runnable.Runnable3;
import com.hugh.byteadvance.ui.basic.handler.runnable.Runnable4;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2021/3/2.
 */
public class HandlerActivity extends Activity {
    public static int SUB1_2_MAIN=0;
    public static int MAIN_2_SUB2=1;
    public static int SUB_2_SUB=2;

    private MyHandler mMyHandler = new MyHandler(this);
    private int mCount = 0;
    private Handler mMainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what== SUB1_2_MAIN){
                Log.e("aaa","I am from Sub thread");
                Log.e("aaa","thread="+Thread.currentThread().getName());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_message);
        mCount = 10;
        findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("aaa", "当前线程");
                        mMyHandler.sendEmptyMessage(1000);
                        Log.e("aaa", "mCount---->" + mCount);
                    }
                }).start();
            }
        });
        findViewById(R.id.btn_send_idle).setOnClickListener((v) -> {
            clickTestIdleHandler();
        });

        findViewById(R.id.btn_send_child_thread).setOnClickListener((v)->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //线程默认是没有关联消息循环器的，在线程中调用Looper.prepare()方法可以将线程与Looper关联起来，
                    // 然后调用Looper.loop()方法可以让线程处理消息，直到Looper停止。
                    //一个线程只能关联一个Looper
                    Looper.prepare();
                    Handler handler = new Handler();
                    Looper.loop();

                }
            }).start();
        });

        //子线程到主线程
        testSub2Main();

        //主线程到子线程
        testMain2Sub();

        //子线程到子线程
        testSub2Sub();
    }
    private void testSub2Sub() {
        Runnable3 runnable3 = new Runnable3();
        new Thread(runnable3).start(); //启动线程3

        //注意：这里由于是线程异步，程序执行到这里的时候，子线程的run方法不一定执行完，可能会导致myLooper3为空，所以，这里循环等待，知道初始化完
        while (true){
            if(runnable3.myLooper3!=null){
                Runnable4 runnable4 = new Runnable4(runnable3.handler3);
                new Thread(runnable4).start();
                return;
            }
        }

    }

    private void testMain2Sub() {
        //主线程向子线程发消息
        Runnable2 runnable2 = new Runnable2();
        new Thread(runnable2).start(); //启动线程2
        Message msg = new Message();
        msg.what= MAIN_2_SUB2;

        //注意：这里由于是线程异步，程序执行到这里的时候，子线程的run方法不一定执行完，可能会导致myLooper3为空，所以，这里循环等待，知道初始化完
        while (true){
            if(runnable2.mylooper2!=null){
                runnable2.handler2.sendMessage(msg);
                return;
            }
        }

    }

    private void testSub2Main() {
        //子线程向主线程发消息
        new Thread(new Runnable1(mMainHandler)).start();
    }



    public void clickTestIdleHandler() {
        // 添加第一个 IdleHandler
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Log.e("Test", "IdleHandler1 queueIdle");
                return false;
            }
        });
        // 添加第二个 IdleHandler
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Log.e("Test", "IdleHandler2 queueIdle");
                return false;
            }
        });
        Handler handler = new Handler();
        // 添加第一个Message
        Message message1 = Message.obtain(handler, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("Test", "message1");
            }
        });
        message1.sendToTarget();
        // 添加第二个Message
        Message message2 = Message.obtain(handler, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("Test", "message2");
            }
        });
        message2.sendToTarget();


    }

    private void handleMessage(Message msg) {
        Log.e("aaa", "收到message" + msg.what);
    }

    static class MyHandler extends Handler {
        private WeakReference<Activity> mReference;

        MyHandler(Activity reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerActivity activity = (HandlerActivity) mReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }


    @Override
    protected void onDestroy() {
        mMyHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
