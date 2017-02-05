package com.alfa.news.model.service;

import android.app.Service;
import android.os.Binder;

public class LocalBinder<S extends Service> extends Binder {

    private final S mService;

    public LocalBinder(S service) {
        mService = service;
    }

    public S getService() {
        return mService;
    }
}