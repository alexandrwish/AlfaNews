package com.alfa.news.di.component;

import com.alfa.news.NewsApplication;
import com.alfa.news.di.module.NewsModule;
import com.alfa.news.model.service.RssService;
import com.alfa.news.view.activity.NewsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NewsModule.class})
public interface NewsComponent {

    void inject(NewsApplication application);

    void inject(NewsActivity activity);

    void inject(RssService service);
}