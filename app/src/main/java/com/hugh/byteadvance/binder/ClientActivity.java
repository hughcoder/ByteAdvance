package com.hugh.byteadvance.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.byteadvance.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2020/8/12.
 */
public class ClientActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "aaa";
    private IRemoteService mRemoteService;
    private boolean mIsBound;
    private TextView mCallBackTv;
    private Button mBtnRegister;
    private Button mBtnOperate;
    private TextView mTvResult;
    private TextView mBtnUnRegister;

    private ICompletedListener completedListener = new ICompletedListener.Stub() {
        @Override
        public void onOperationCompleted(final MyData result) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvResult.setText("运算结果： 加法：" + result.getData1() + "------运算结果: 乘法：" + result.getData2());
                }
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        mCallBackTv = (TextView) findViewById(R.id.tv_callback);
        mTvResult = findViewById(R.id.tv_operate_result);
        mBtnOperate = findViewById(R.id.btn_operate);
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnUnRegister = findViewById(R.id.btn_un_register);
        mBtnRegister.setOnClickListener(this);
        mBtnOperate.setOnClickListener(this);
        mBtnUnRegister.setOnClickListener(this);
        mCallBackTv.setText("unattached");
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
            String pidInfo = null;
            MyData myData = null;
            try {
                myData = mRemoteService.getMyData();
                pidInfo = "pid=" + mRemoteService.getPid() +
                        ", data1 = " + myData.getData1() +
                        ", data2=" + myData.getData2();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "[ClientActivity] onServiceConnected  " + pidInfo);
            mCallBackTv.setText(pidInfo);
            Toast.makeText(ClientActivity.this, "connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "[ClientActivity] onServiceDisconnected");
            mCallBackTv.setText("disconnected");
            mRemoteService = null;
            Toast.makeText(ClientActivity.this, "disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                /**
                 * 注册监听
                 */
                if (mRemoteService == null) {
                    Toast.makeText(ClientActivity.this, "请先点击bind,建立链接", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(ClientActivity.this, "注册监听成功", Toast.LENGTH_SHORT).show();
                        mRemoteService.registerListener(completedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_operate:
                /**
                 * 调用Service异步方法
                 */
                if (mRemoteService == null) {
                    Toast.makeText(ClientActivity.this, "请先点击bind,建立链接", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.e("aaa", "送入数据");
                        MyData myData = new MyData(2, 88);
                        mRemoteService.operation(myData);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_un_register:
                /**
                 * 取消监听
                 */
                if (mRemoteService == null) {
                    Toast.makeText(ClientActivity.this, "请先点击bind,建立链接", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        mRemoteService.unregisterListener(completedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    public void clickHandler(View view) {
        switch (view.getId()) {
            case R.id.btn_bind:
                bindRemoteService();
                break;

            case R.id.btn_unbind:
                unbindRemoteService();
                break;

            case R.id.btn_kill:
                killRemoteService();
                break;
        }
    }

    /**
     * 绑定远程服务
     */
    private void bindRemoteService() {
        Log.i(TAG, "[ClientActivity] bindRemoteService");
        Intent intent = new Intent(ClientActivity.this, RemoteService.class);
        intent.setAction(IRemoteService.class.getName());
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        mIsBound = true;
        mCallBackTv.setText("binding中");
    }

    /**
     * 解除绑定远程服务
     */
    private void unbindRemoteService() {
        if (!mIsBound) {
            return;
        }
        Log.i(TAG, "[ClientActivity] unbindRemoteService ==>");
        unbindService(mConnection);
        mIsBound = false;
        mCallBackTv.setText("unbinding...");
    }


    /**
     * 杀死远程服务
     */
    private void killRemoteService() {
        Log.i(TAG, "[ClientActivity] killRemoteService");
        try {
            android.os.Process.killProcess(mRemoteService.getPid());
            mCallBackTv.setText("kill success");
        } catch (RemoteException e) {
            e.printStackTrace();
            Toast.makeText(ClientActivity.this, "kill failure", Toast.LENGTH_SHORT).show();
        }
    }

}
