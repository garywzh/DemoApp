package com.garywzh.demoapp;

import android.app.Application;
import com.facebook.stetho.Stetho;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by garywzh on 2017/4/12.
 */

public class DemoApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        appComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this)).build();
    }

    public AppComponent injector() {
        return appComponent;
    }

    @Module
    class AppModule {

        private Application application;

        AppModule(Application application) {
            this.application = application;
        }

        @Provides
        @Singleton
        Application provideApplication() {
            return application;
        }
    }
}

