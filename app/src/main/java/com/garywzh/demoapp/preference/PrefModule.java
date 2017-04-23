package com.garywzh.demoapp.preference;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class PrefModule {

    private static final String PREFERENCES_NAME = "app";

    private static final String DEFAULT_USERNAME = "garywzh";
    private static final String KEY_USERNAME = "username";

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @Username
    StringPreference provideUsername(SharedPreferences prefs) {
        return new StringPreference(prefs, KEY_USERNAME, DEFAULT_USERNAME);
    }
}
