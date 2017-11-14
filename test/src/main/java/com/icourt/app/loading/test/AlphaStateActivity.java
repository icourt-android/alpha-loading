package com.icourt.app.loading.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.icourt.loading.AlphaStateLayout;

/**
 * @author youxuan  E-mail:xuanyouwu@163.com
 * @version 2.2.1
 * @Description
 * @Company Beijing icourt
 * @date createTime：2017/11/14
 */
public class AlphaStateActivity extends AppCompatActivity {
    private ListView mList;
    private AlphaStateLayout mAlphaStateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_state);
        initView();
    }

    private void initView() {
        mList = (ListView) findViewById(R.id.list);
        mAlphaStateLayout = (AlphaStateLayout) findViewById(R.id.alphaStateLayout);
        String[] data = new String[100];
        for (int i = 0; i < 100; i++) {
            data[i] = "Row " + i;
        }
        mList.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_state, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.error:
                mAlphaStateLayout.setViewState(AlphaStateLayout.VIEW_STATE_ERROR);
                return true;
            case R.id.empty:
                mAlphaStateLayout.setViewState(AlphaStateLayout.VIEW_STATE_EMPTY);
                return true;
            case R.id.content:
                mAlphaStateLayout.setViewState(AlphaStateLayout.VIEW_STATE_CONTENT);
                return true;
            case R.id.loading:
                mAlphaStateLayout.setViewState(AlphaStateLayout.VIEW_STATE_LOADING);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
