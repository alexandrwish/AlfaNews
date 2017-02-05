package com.alfa.news.model.loader;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alfa.news.NewsApplication;
import com.alfa.news.common.Constants;
import com.alfa.news.http.RssClient;
import com.alfa.news.model.entity.ItemEntity;
import com.alfa.news.model.entity.ItemEntityDao;
import com.alfa.news.model.record.ItemRecord;
import com.alfa.news.model.record.RssRecord;
import com.alfa.news.model.service.RssService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NewsLoader {

    private final RssClient mClient;
    private final SharedPreferences mPreferences;

    @Inject
    public NewsLoader(RssClient client) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(NewsApplication.getInstance());
        mClient = client;
    }

    public Observable<List<ItemRecord>> load() {
        return mClient.load(Constants.RSS_LINK)
                .observeOn(Schedulers.io())
                .filter(new Func1<RssRecord, Boolean>() {
                    public Boolean call(RssRecord rssRecord) {
                        return !mPreferences.getString(Constants.UPDATE_VERSION, "").equalsIgnoreCase(rssRecord.getChannel().getDate());
                    }
                })
                .doOnNext(new Action1<RssRecord>() {
                    public void call(RssRecord rssRecord) {
                        mPreferences.edit().putString(Constants.UPDATE_VERSION, rssRecord.getChannel().getDate()).apply();
                    }
                })
                .map(new Func1<RssRecord, List<ItemRecord>>() {
                    public List<ItemRecord> call(RssRecord rssRecord) {
                        return rssRecord.getChannel().getItems();
                    }
                })
                .map(new Func1<List<ItemRecord>, List<ItemRecord>>() {
                    public List<ItemRecord> call(List<ItemRecord> records) {
                        for (ItemRecord record : records) {
                            convertDateFormat(record);
                        }
                        return records;
                    }
                })
                .doOnNext(new Action1<List<ItemRecord>>() {
                    public void call(List<ItemRecord> records) {
                        save(records);
                    }
                });
    }

    public Observable<List<ItemRecord>> loadAll() {
        return NewsApplication.getInstance().getSession().getItemEntityDao().rx().loadAll()
                .subscribeOn(Schedulers.io())
                .map(convert());
    }

    private Func1<List<ItemEntity>, List<ItemRecord>> convert() {
        return new Func1<List<ItemEntity>, List<ItemRecord>>() {
            public List<ItemRecord> call(List<ItemEntity> entities) {
                List<ItemRecord> records = new ArrayList<>(entities.size());
                for (ItemEntity entity : entities) {
                    records.add(new ItemRecord(entity.getTitle(),
                            entity.getLink(),
                            entity.getDate(),
                            entity.getDescription()));
                }
                return records;
            }
        };
    }

    private void convertDateFormat(ItemRecord record) {
        try {
            DateFormat fromFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.UK);
            fromFormat.setLenient(false);
            DateFormat toFormat = new SimpleDateFormat("d MMM yyyy HH:mm", new Locale("ru"));
            toFormat.setLenient(false);
            record.setDate(toFormat.format(fromFormat.parse(record.getDate())));
        } catch (ParseException e) {
            Log.e(RssService.class.getName(), e.getMessage(), e);
        }
    }

    private ItemEntity convert(ItemRecord record) {
        return new ItemEntity(null,
                record.getTitle(),
                record.getLink(),
                record.getDate(),
                record.getDescription());
    }

    private void save(List<ItemRecord> records) {
        ItemEntityDao dao = NewsApplication.getInstance().getSession().getItemEntityDao();
        dao.deleteAll();
        for (ItemRecord record : records) {
            dao.insert(convert(record));
        }
    }
}