package com.hugh.byteadvance.dragvideo.videopage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.dragvideo.DragVideoLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2020/9/17.
 */
public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView mVideoView;
    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private DragVideoLayout dragVideoLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedioview);
        initViews();
        mVideoView.setVideoPath("https://cdn.qupeiyin.cn/2019-01-16/1547620040178.mp4");
//        mVideoView.setMediaController(new MediaController(this));
    }

    private void initViews() {
        mVideoView = (VideoView) findViewById(R.id.video_view);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        dragVideoLayout = findViewById(R.id.dragVideoLayout);

        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        findViewById(R.id.btn_start_sub).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mVideoView.start();
                break;
            case R.id.btn_pause:
                mVideoView.pause();
                break;
            case R.id.btn_stop:
                mVideoView.stopPlayback();
                break;
            case R.id.btn_start_sub:
                dragVideoLayout.startPlay("https://cdn.qupeiyin.cn/2020-06-10/ac038cefabc0b746400e4cc6300fa3a4.mp4");
                break;
        }
    }

}
