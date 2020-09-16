package com.hugh.byteadvance.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.databinding.ActivityViewModelBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by chenyw on 2020/9/16.
 */
public class ViewModelActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityViewModelBinding mViewModelBinding;
    private UserViewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        mViewModelBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);
        mViewModelBinding.setClick(this);
        mModel = new ViewModelProvider(this.getViewModelStore(),
                ViewModelFactory.getInstance()).get(UserViewModel.class);
        mModel.getUsers().observe(this, users -> {
            // update UI
            Log.e("aaa", "收到了");
        });
        mModel.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String curName) {
                Log.e("aaa", "修改model的数据");
                mViewModelBinding.tvName.setText(curName);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click) {
            double index = (int) (Math.random() * 100);
            String anotherName = "随机展示：" + index;
            mModel.getCurrentName().setValue(anotherName);
        }
    }
}
