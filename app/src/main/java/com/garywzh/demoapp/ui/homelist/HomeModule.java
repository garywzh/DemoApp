package com.garywzh.demoapp.ui.homelist;

import com.garywzh.demoapp.ui.homelist.HomeContract.HomePresenter;
import com.garywzh.demoapp.network.GithubApi;
import com.garywzh.demoapp.preference.StringPreference;
import com.garywzh.demoapp.preference.Username;
import com.squareup.sqlbrite.BriteDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by garywzh on 2017/4/12.
 */

@Module
public class HomeModule {

    @Provides
    @Singleton
    HomePresenter provideHomePresenter(GithubApi githubApi, BriteDatabase database,
        @Username StringPreference username) {
        return new HomePresenterImpl(githubApi, database, username);
    }
}
