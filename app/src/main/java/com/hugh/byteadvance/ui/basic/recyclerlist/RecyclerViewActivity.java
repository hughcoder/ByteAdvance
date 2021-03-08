package com.hugh.byteadvance.ui.basic.recyclerlist;

import android.app.Activity;
import android.os.Bundle;

import com.hugh.byteadvance.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenyw on 2021/3/2.
 */
public class RecyclerViewActivity extends Activity {

    private RecyclerView recyclerView;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = findViewById(R.id.rv_recycler);
        initData();
        HomeAdapter adapter = new HomeAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'k'; i++) {
            mDatas.add("" + (char) i);
        }
    }

}
