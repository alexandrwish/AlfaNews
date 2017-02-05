package com.alfa.news;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.alfa.news.db.RssDBAdapter;
import com.alfa.news.di.component.DaggerNewsComponent;
import com.alfa.news.di.component.NewsComponent;
import com.alfa.news.di.module.NewsModule;
import com.alfa.news.http.RssClient;
import com.alfa.news.model.entity.DaoSession;
import com.alfa.news.model.service.LocalBinder;
import com.alfa.news.model.service.RssService;
import com.alfa.news.view.activity.NewsActivity;

import javax.inject.Inject;

public class NewsApplication extends Application {

    private static NewsApplication sInstance;
    @Inject
    RssClient mClient;
    @Inject
    RssDBAdapter mAdapter;
    private NewsComponent mComponent;
    private LocalBinder<RssService> mRssBinder;
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRssBinder = (LocalBinder<RssService>) service;
        }

        public void onServiceDisconnected(ComponentName name) {
            mRssBinder = null;
        }
    };

    public static NewsApplication getInstance() {
        return sInstance;
    }

    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mComponent = DaggerNewsComponent.builder().newsModule(new NewsModule()).build();
        mComponent.inject(this);
        bindService(new Intent(NewsApplication.this, RssService.class), mConnection, BIND_AUTO_CREATE);
    }

    public RssService getRssService() {
        return mRssBinder != null ? mRssBinder.getService() : null;
    }

    public void inject(RssService service) {
        mComponent.inject(service);
    }

    public void inject(NewsActivity activity) {
        mComponent.inject(activity);
    }

    public RssClient getClient() {
        return mClient;
    }

    public DaoSession getSession() {
        return mAdapter != null ? mAdapter.getSession() : null;
    }
}