package com.hugh.byteadvance.annotation;

import android.app.Activity;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by chenyw on 2020/10/20.
 */
public class AnnoActivity extends Activity {

    @AuthorAnno(name = "作家A", website = "www.xxx.com")
    Author authorA;

    Author authorB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnnotationParser.getInstance().inject(this);
        if (authorA != null) {
            Log.e("aaa", "authorA是" + authorA.name + "网站是" + authorA.website);
        } else {
            Log.e("aaa", "authorA 为空");
        }

        if (authorB != null) {
            Log.e("aaa", "authorB是" + authorB.name + "网站是" + authorB.website);
        } else {
            Log.e("aaa", "authorB 为空");
        }



    }
}
