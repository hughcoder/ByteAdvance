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
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.byteadvance.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2020/8/12.
 */
public class ClientActivity extends AppCompatActivity   {
    private static final String TAG = "aaa";
    private IRemoteService mRemoteService;
    private boolean mIsBound;
    private TextView mCallBackTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        mCallBackTv = (TextView) findViewById(R.id.tv_callback);
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


    public void clickHandler(View view){
        switch (view.getId()){
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
