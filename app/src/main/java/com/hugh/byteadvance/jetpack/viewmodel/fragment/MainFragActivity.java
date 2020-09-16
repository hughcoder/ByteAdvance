package com.hugh.byteadvance.jetpack.viewmodel.fragment;

import android.os.Bundle;

import com.hugh.byteadvance.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by chenyw on 2020/9/16.
 */
public class MainFragActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
    }
}
