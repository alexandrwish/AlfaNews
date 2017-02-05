package com.alfa.news.db;

import android.content.Context;

import com.alfa.news.model.entity.DaoMaster;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

class RssOpenHelper extends DatabaseOpenHelper {

    RssOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }

    public void onCreate(Database db) {
        DaoMaster.createAllTables(db, false);
    }

    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
    }
}