package com.alfa.news.listener;

import com.alfa.news.model.record.ShortNews;

import java.util.List;

public interface NewsListener {

    void register();

    void unRegister();

    void update();

    void stopRefresh();

    void show(ShortNews item, List<ShortNews> records);
}