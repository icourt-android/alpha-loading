package com.icourt.app.loading.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.icourt.loading.AlphaLoading;

import java.util.Random;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void onClick(View view) {
        final AlphaLoading alphaLoading = new AlphaLoading.Builder(this)
                .message("我在加载...")
                .cancelable(true)
                .messageVisible(true)
                .resultDuration(3000)
//                .loadDrawable(R.drawable.ic_launcher_background)
                .okIcon(R.drawable.ic_launcher_background)
                .failIcon(R.drawable.alpha_progress_bg)
                .create();
//        alphaLoading.setMessage("我在加载...");
//        alphaLoading.dismissImmediately();
//        alphaLoading.dismissOk("111111");
//        alphaLoading.show();
        alphaLoading.show();

//        alphaLoading.dismissImmediately();
//        alphaLoading.dismissOk("成功");
//        alphaLoading.dismissFail("加载失败");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alphaLoading.setMessage("正在上传...");
            }
        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                alphaLoading.show();
                if (new Random().nextBoolean()) {
                    alphaLoading.dismissOk("成功");
                } else {
                    alphaLoading.dismissFail("加载失败了");
                }
//                alphaLoading.show();
            }
        }, 4000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                alphaLoading.dismissImmediately();
//            }
//        }, 2800);    // 800 2800
    }
}
