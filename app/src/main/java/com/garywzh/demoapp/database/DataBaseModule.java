package com.garywzh.demoapp.database;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import rx.schedulers.Schedulers;

/**
 * Created by garywzh on 2017/4/13.
 */

@Module
public class DataBaseModule {

    @Provides
    @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper(Application application){
        return OpenHelper.getInstance(application);
    }

    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase(SQLiteOpenHelper openHelper){
        return new SqlBrite.Builder().build().wrapDatabaseHelper(openHelper, Schedulers.io());
    }
}
