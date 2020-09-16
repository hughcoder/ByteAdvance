package com.hugh.byteadvance.jetpack.databing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.databinding.ActivityDatabingBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * Created by chenyw on 2020/9/16.
 */
public class DataBindingActivity extends Activity implements View.OnClickListener {

    private UserBean userBean;
    private ActivityDatabingBinding mDatabinding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabinding = DataBindingUtil.setContentView(this, R.layout.activity_databing);
        userBean = new UserBean("Test", "User");
        mDatabinding.setUser(userBean);
        mDatabinding.setClick(this);
        //可以使用 LayoutInflater 获取视图
        //ActivityDatabingBinding binding = ActivityDatabingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click) {
            Log.e("aaa", "点击修改数据");
            mDatabinding.tvFirstName.setText("改变一下");
        } else if (v.getId() == R.id.btn_click_two) {
            Log.e("aaa", "点击修改数据22");
            userBean.setFirstName("哈哈哈");
        }
    }
}
