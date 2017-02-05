package com.alfa.news.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alfa.news.R;
import com.alfa.news.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends Fragment {

    @BindView(R.id.content)
    WebView mContent;
    private String mUrl;
    private Unbinder mBinder;

    public static NewsFragment newInstance(String url) {
        Bundle bundle = new Bundle(1);
        bundle.putString(Constants.CURRENT_ITEM, url);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(Constants.CURRENT_ITEM);
    }

    @Nullable
    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mBinder = ButterKnife.bind(this, view);
        mContent.getSettings().setJavaScriptEnabled(true);
        mContent.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mContent.loadUrl(mUrl);
        return view;
    }

    public void onDestroyView() {
        mBinder.unbind();
        super.onDestroyView();
    }
}