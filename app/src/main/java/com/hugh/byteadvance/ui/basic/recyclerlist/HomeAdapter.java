package com.hugh.byteadvance.ui.basic.recyclerlist;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.byteadvance.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenyw on 2021/3/2.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<String> mDatas;
    private Activity mActivity;
    private static int mCount = 0;

    public HomeAdapter(Activity activity, List<String> datas) {
        mActivity = activity;
        mDatas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCount++;
        Log.e("aaa", "onCreateViewHolder--->" + mCount);
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mActivity).inflate(R.layout.adapter_item_home, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.e("aaa", "onBindViewHolder--->" + position);
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_num);
        }
    }
}
