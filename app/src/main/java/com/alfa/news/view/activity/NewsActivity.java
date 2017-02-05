package com.alfa.news.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.alfa.news.NewsApplication;
import com.alfa.news.R;
import com.alfa.news.common.Constants;
import com.alfa.news.listener.NewsListener;
import com.alfa.news.model.record.ShortNews;
import com.alfa.news.presenter.NewsPresenter;
import com.alfa.news.view.adapter.ItemsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;

@Layout(R.layout.activity_news)
public class NewsActivity extends BaseActivity implements NewsListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.news)
    ListView mNews;
    @BindView(R.id.container_rss)
    SwipeRefreshLayout mSwipeContainer;
    @Inject
    NewsPresenter mPresenter;
    private BroadcastReceiver mReceiver;
    private IntentFilter mFilter;
    private ItemsAdapter mAdapter;

    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void initActivity() {

        NewsApplication.getInstance().inject(this);
        mPresenter.setListener(this);
        mFilter = new IntentFilter(Constants.UPDATE_RSS);
        mAdapter = new ItemsAdapter(this, mPresenter.getData());
        mNews.setAdapter(mAdapter);
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                mPresenter.onReceive();
            }
        };
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    @OnItemClick(R.id.news)
    public void onClick(int position) {
        mPresenter.onClick(mAdapter.getItem(position));
    }

    public void register() {
        registerReceiver(mReceiver, mFilter);
    }

    public void unRegister() {
        unregisterReceiver(mReceiver);
    }

    public void show(ShortNews item, List<ShortNews> records) {
        Parcelable[] parcelables = new Parcelable[records.size()];
        for (int i = 0; i < records.size(); i++) {
            parcelables[i] = records.get(i);
        }
        startActivity(new Intent(NewsActivity.this, ItemActivity.class)
                .putExtra(Constants.CURRENT_ITEM, item)
                .putExtra(Constants.ALL_ITEMS, parcelables));
    }

    public void update() {
        if (mAdapter != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void stopRefresh() {
        runOnUiThread(new Runnable() {
            public void run() {
                mSwipeContainer.setRefreshing(false);
            }
        });
    }

    public void onRefresh() {
        mPresenter.load();
    }
}