package com.alfa.news.model.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alfa.news.NewsApplication;
import com.alfa.news.common.Constants;
import com.alfa.news.model.loader.NewsLoader;
import com.alfa.news.model.record.ItemRecord;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RssService extends Service {

    @Inject
    NewsLoader mLoader;

    public void onCreate() {
        super.onCreate();
        NewsApplication.getInstance().inject(this);
        Observable.interval(0, Constants.MAX_INTERVAL, TimeUnit.SECONDS, Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {

                    }

                    public void onError(Throwable e) {
                        Log.e(RssService.class.getName(), e.getMessage(), e);
                    }

                    public void onNext(Long aLong) {
                        mLoader.load()
                                .subscribe(new Action1<List<ItemRecord>>() {
                                    public void call(List<ItemRecord> records) {
                                        sendBroadcast(new Intent(Constants.UPDATE_RSS));
                                    }
                                }, new Action1<Throwable>() {
                                    public void call(Throwable e) {
                                        Log.e(RssService.class.getName(), e.getMessage(), e);
                                    }
                                });
                    }
                });
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return new LocalBinder<>(this);
    }
}