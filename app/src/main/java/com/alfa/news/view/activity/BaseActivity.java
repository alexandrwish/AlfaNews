package com.alfa.news.view.activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends Activity {

    private Unbinder mBinder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getClass().isAnnotationPresent(Layout.class)) {
            setContentView(getClass().getAnnotation(Layout.class).value());
        }
        mBinder = ButterKnife.bind(this);
        initActivity();
    }

    protected void onDestroy() {
        mBinder.unbind();
        super.onDestroy();
    }

    protected abstract void initActivity();
}