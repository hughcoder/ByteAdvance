package com.hugh.byteadvance.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by chenyw on 2020/8/12.
 */
public class RemoteService extends Service {

    private static final String TAG = "aaa";

    MyData mMyData;
    //声明
    private RemoteCallbackList<ICompletedListener> callbackList;

    @Override
    public void onCreate() {
        super.onCreate();
        initMyData();
        callbackList = new RemoteCallbackList<>();
    }


    /**
     * 初始化MyData数据
     **/
    private void initMyData() {
        mMyData = new MyData();
        mMyData.setData1(10);
        mMyData.setData2(20);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            Log.e(TAG, "android.os.Process.myPid()" + android.os.Process.myPid());
            return android.os.Process.myPid();
        }

        @Override
        public MyData getMyData() throws RemoteException {
            Log.i(TAG, "[RemoteService] getMyData()  " + mMyData.toString());
            return mMyData;
        }

        @Override
        public void operation(MyData parameter1 ) throws RemoteException {
            try {
                Log.e(TAG, "operation 被调用，延时3秒，模拟耗时计算");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int param1 = parameter1.getData1();
            int param2 = parameter1.getData2();
            MyData result = new MyData(param1 + param2,param1 *param2);
            //在操作 RemoteCallbackList 前，必须先调用其 beginBroadcast 方法
            //此外，beginBroadcast 必须和 finishBroadcast配套使用
            int count = callbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                ICompletedListener listener = callbackList.getBroadcastItem(i);
                if (listener != null) {
                    listener.onOperationCompleted(result);
                }
            }
        }

        @Override
        public void registerListener(ICompletedListener listener) throws RemoteException {
            callbackList.register(listener);
            Log.e(TAG, "registerListener 注册回调成功");
        }

        @Override
        public void unregisterListener(ICompletedListener listener) throws RemoteException {
            callbackList.unregister(listener);
            Log.e(TAG, "unregisterListener 解除注册回调成功");
        }

        //此处可以用于权限拦截
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "[RemoteService] onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "[RemoteService] onDestroy");
        super.onDestroy();
    }

}
