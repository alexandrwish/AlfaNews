package com.alfa.news.di.module;

import com.alfa.news.db.RssDBAdapter;
import com.alfa.news.http.RssClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class NewsModule {

    @Singleton
    @Provides
    public RssClient provideRssClient() {
        return new Retrofit.Builder()
                .baseUrl("https://alfabank.ru")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(RssClient.class);
    }

    @Singleton
    @Provides
    public RssDBAdapter provideDBAdapter() {
        return new RssDBAdapter();
    }
}