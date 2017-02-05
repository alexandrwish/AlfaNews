package com.alfa.news.db;

import com.alfa.news.NewsApplication;
import com.alfa.news.model.entity.DaoMaster;
import com.alfa.news.model.entity.DaoSession;

public class RssDBAdapter {

    private static final String DATABASE_NAME = "rss_name";
    private static final Integer DATABASE_VERSION = 1;

    private final DaoSession mSession;

    public RssDBAdapter() {
        mSession = new DaoMaster(new RssOpenHelper(NewsApplication.getInstance(), DATABASE_NAME, DATABASE_VERSION).getWritableDatabase()).newSession();
    }

    public DaoSession getSession() {
        return mSession;
    }
}