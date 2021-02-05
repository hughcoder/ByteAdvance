package com.hugh.byteadvance.hook;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.hook.wifihook.WifiHook;
import com.hugh.byteadvance.hook.wifimanager.WifiBinderHookHelper;

import androidx.annotation.Nullable;

/**
 * Created by chenyw on 2021/1/7.
 */
public class HookActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);

        try {
            BinderHookHelper.hookClipboardService();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            WifiBinderHookHelper.hookwiFiService();
//        } catch (Exception e) {
//            Log.e("aaa", "eeee");
//            e.printStackTrace();
//        }
        WifiHook.hook(this.getApplicationContext());

        WifiManager wifiMan = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        WifiInfo wifiInfo = wifiMan.getConnectionInfo();

        if(wifiInfo!=null){
            Log.e("aaa","调用了哈啊哈");
        }
    }
}
