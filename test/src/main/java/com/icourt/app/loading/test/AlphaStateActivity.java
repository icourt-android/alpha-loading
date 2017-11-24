package com.icourt.app.loading.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.icourt.loading.AlphaStateLayout;

import static com.icourt.loading.ViewState.VIEW_STATE_CONTENT;
import static com.icourt.loading.ViewState.VIEW_STATE_EMPTY;
import static com.icourt.loading.ViewState.VIEW_STATE_ERROR;
import static com.icourt.loading.ViewState.VIEW_STATE_LOADING;

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

    RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_state);
        initView();
    }

    private void initView() {

        mList = (ListView) findViewById(R.id.list);
        mAlphaStateLayout = (AlphaStateLayout) findViewById(R.id.alphaStateLayout);
        mAlphaStateLayout.setErrorRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "xxx", Toast.LENGTH_SHORT).show();
            }
        });
        String[] data = new String[100];
        for (int i = 0; i < 100; i++) {
            data[i] = "Row " + i;
        }
        mList.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data));
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mAlphaStateLayout.setContentLoadingCoexist(checkedId == R.id.radio0);
                mAlphaStateLayout.setContentEmptyCoexist(checkedId == R.id.radio1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_state, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for (int i = 0; i < mAlphaStateLayout.getChildCount(); i++) {
            View childAt = mAlphaStateLayout.getChildAt(i);
            Log.d("------------>child:", "" + i + " c:" + childAt);
        }
        switch (item.getItemId()) {
            case R.id.error:
                mAlphaStateLayout.setViewState(VIEW_STATE_ERROR);
                return true;
            case R.id.empty:
                mAlphaStateLayout.setViewState(VIEW_STATE_EMPTY);
                return true;
            case R.id.content:
                mAlphaStateLayout.setViewState(VIEW_STATE_CONTENT);
                return true;
            case R.id.loading:
                mAlphaStateLayout.setViewState(VIEW_STATE_LOADING);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
