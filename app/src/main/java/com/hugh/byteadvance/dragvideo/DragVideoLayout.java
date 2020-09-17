package com.hugh.byteadvance.dragvideo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.hugh.byteadvance.R;
import com.hugh.byteadvance.util.CommonUtils;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by chenyw on 2020/9/17.
 */
public class DragVideoLayout extends LinearLayout {

    private ViewDragHelper mDragger;
    private VideoView mVideoView;


    public DragVideoLayout(Context context) {
        super(context);
    }

    public void addVideoView(Context context) {
        mVideoView = new VideoView(context);
        this.addView(mVideoView);
        LinearLayout.LayoutParams lp = (LayoutParams) mVideoView.getLayoutParams();
        lp.width = CommonUtils.dp2px(context, 100);
        lp.height = CommonUtils.dp2px(context, 100);
        mVideoView.setLayoutParams(lp);
        mVideoView.setZOrderOnTop(true);
        mVideoView.setVideoPath("https://cdn.qupeiyin.cn/2019-01-16/1547620040178.mp4");
    }

    public void addTextView(Context context) {
        TextView textView = new TextView(context);
        addView(textView);
        LinearLayout.LayoutParams lp = (LayoutParams) textView.getLayoutParams();
        lp.width = CommonUtils.dp2px(context, 100);
        lp.height = CommonUtils.dp2px(context, 100);
        textView.setLayoutParams(lp);
        textView.setText("hdsodfoisjodfjids");
        textView.setTextColor(Color.parseColor("#FFFFFF"));

    }


    public void startPlay(String url) {
        mVideoView.start();
    }

    public DragVideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addVideoView(context);
        addTextView(context);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //取得左边界的坐标
                final int leftBound = getPaddingLeft();
                //取得右边界的坐标
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
                //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
                //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
                return Math.min(Math.max(left, leftBound), rightBound);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
                return Math.min(Math.max(top, topBound), bottomBound);
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(final View releasedChild, float xvel, float yvel) {
                invalidate();
            }

        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //决定是否拦截当前事件
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //处理事件
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
