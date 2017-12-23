package com.icourt.app.loading.test;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.icourt.loading.AlphaLoading;

/**
 * Description
 * Company Beijing iCourt
 *
 * @author Junkang.Ding Email:dingjunkang@icourt.cc
 *         date createTime：2017/12/23
 *         version 2.2.2
 */
public class TestActivity extends AppCompatActivity {

    private AlphaLoading loading;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        loading = new AlphaLoading.Builder(this).cancelable(true).resultDuration(2000).create();

        progressDialog = new ProgressDialog(this);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(TestActivity.this, "dismissed", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(TestActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loading.dismissImmediatelyLossState();
    }

    public void onClick1(View view) {
        loading.setMessage("click 1");
        loading.show();

//        progressDialog.setMessage("click 1");
//        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onClick2(null);
            }
        }, 1000);
    }

    public void onClick2(View view) {
        loading.dismissImmediatelyLossState();
        loading.setMessage("click 2");
        loading.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismissOk("OK", new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TestActivity.this, "End Action", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);

//        progressDialog.dismiss();
//        progressDialog.setMessage("click 2");
//        progressDialog.show();
    }

}
