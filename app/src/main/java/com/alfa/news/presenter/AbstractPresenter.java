package com.alfa.news.presenter;

public abstract class AbstractPresenter<L> {

    protected L mListener;

    public abstract void onStart();

    public abstract void onStop();

    public void setListener(L listener) {
        mListener = listener;
    }
}