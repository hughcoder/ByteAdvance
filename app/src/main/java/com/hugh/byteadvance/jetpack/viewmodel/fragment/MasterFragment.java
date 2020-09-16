package com.hugh.byteadvance.jetpack.viewmodel.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.jetpack.viewmodel.UserViewModel;
import com.hugh.byteadvance.jetpack.viewmodel.ViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by chenyw on 2020/9/16.
 */
public class MasterFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private Button mBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout rootView = (ConstraintLayout) inflater.inflate(R.layout.fragment_master, container, false);
        mBtn = rootView.findViewById(R.id.btn_change);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //这两个 Fragment 都会检索包含它们的 Activity。这样，当这两个 Fragment 各自获取 ViewModelProvider 时，
        // 它们会收到相同的 SharedViewModel 实例（其范围限定为该 Activity）。
        sharedViewModel =  new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        mBtn.setOnClickListener(v -> {
            Log.e("aaa", "点击---->");
            double index = (int) (Math.random() * 100);
            String anotherName = "随机展示：" + index;
            sharedViewModel.select(anotherName);
        });

    }
}
