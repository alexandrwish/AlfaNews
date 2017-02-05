package com.alfa.news.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.alfa.news.R;
import com.alfa.news.common.Constants;
import com.alfa.news.model.record.ShortNews;
import com.alfa.news.view.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Layout(R.layout.activity_item)
public class ItemActivity extends FragmentActivity {

    @BindView(R.id.pager)
    ViewPager mPager;

    private Unbinder mBinder;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

    protected void initActivity() {
        Parcelable[] records = getIntent().getParcelableArrayExtra(Constants.ALL_ITEMS);
        ShortNews record = getIntent().getParcelableExtra(Constants.CURRENT_ITEM);
        List<ShortNews> items = new ArrayList<>(records.length);
        int i = 0, j = 0;
        for (Parcelable parcelable : records) {
            items.add((ShortNews) parcelable);
            if (!Objects.equals(record.getUrl(), ((ShortNews) parcelable).getUrl())) {
                i++;
            } else {
                j = i;
            }
            PagerAdapter adapter = new NewsAdapter(getSupportFragmentManager(), items);
            mPager.setAdapter(adapter);
            mPager.setCurrentItem(j, true);
        }

    }
}