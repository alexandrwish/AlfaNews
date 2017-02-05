package com.alfa.news.http;

import com.alfa.news.model.record.RssRecord;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface RssClient {

    @GET
    Observable<RssRecord> load(@Url String url);
}
