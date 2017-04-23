package com.garywzh.demoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.garywzh.demoapp.model.Repo;

/**
 * Created by garywzh on 2017/4/13.
 */

class OpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "TestCache.db";
    static final int DB_VERSION = 1;
    private static OpenHelper instance;
    static OpenHelper getInstance(Context context) {
        if (null == instance) {
            instance = new OpenHelper(context);
        }
        return instance;
    }
    private OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Repo.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade logic
    }
}
