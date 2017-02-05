package com.alfa.news.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alfa.news.model.record.ShortNews;
import com.alfa.news.view.fragment.NewsFragment;

import java.util.List;

public class NewsAdapter extends FragmentPagerAdapter {

    private final List<ShortNews> mRecords;

    public NewsAdapter(FragmentManager fm, List<ShortNews> records) {
        super(fm);
        mRecords = records;
    }

    public Fragment getItem(int position) {
        return NewsFragment.newInstance(mRecords.get(position).getUrl());
    }

    public int getCount() {
        return mRecords.size();
    }

    public CharSequence getPageTitle(int position) {
        return mRecords.get(position).getDate();
    }
}