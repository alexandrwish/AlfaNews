package com.alfa.news.presenter;

import android.util.Log;

import com.alfa.news.listener.NewsListener;
import com.alfa.news.model.loader.NewsLoader;
import com.alfa.news.model.record.ItemRecord;
import com.alfa.news.model.record.ShortNews;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NewsPresenter extends AbstractPresenter<NewsListener> {

    private final List<ItemRecord> mRecords = new LinkedList<>();
    private final NewsLoader mLoader;

    @Inject
    public NewsPresenter(NewsLoader loader) {
        mLoader = loader;
        mLoader.loadAll().subscribe(new Action1<List<ItemRecord>>() {
            public void call(List<ItemRecord> records) {
                mRecords.addAll(records);
                mListener.update();
            }
        });
    }

    public void onStart() {
        mListener.register();
    }

    public void onStop() {
        mListener.unRegister();
    }

    public void onReceive() {
        mLoader.loadAll().subscribe(new Action1<List<ItemRecord>>() {
            public void call(List<ItemRecord> records) {
                mRecords.addAll(records);
                mListener.update();
            }
        });
    }

    public void onClick(ItemRecord item) {
        List<ShortNews> news = new ArrayList<>(mRecords.size());
        for (ItemRecord record : mRecords) {
            news.add(new ShortNews(record.getLink(), record.getDate()));
        }
        mListener.show(new ShortNews(item.getLink(), item.getDate()), news);
    }

    public List<ItemRecord> getData() {
        return mRecords;
    }

    public void load() {
        mLoader.load()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ItemRecord>>() {
                    public void onCompleted() {
                        mListener.stopRefresh();
                    }

                    public void onError(Throwable e) {
                        Log.e(NewsPresenter.class.getName(), e.getMessage(), e);
                        mListener.stopRefresh();
                    }

                    public void onNext(List<ItemRecord> records) {
                        mRecords.clear();
                        mRecords.addAll(records);
                        mListener.update();
                        mListener.stopRefresh();
                    }
                });
    }
}