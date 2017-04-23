package com.garywzh.demoapp;

import com.garywzh.demoapp.DemoApplication.AppModule;
import com.garywzh.demoapp.database.DataBaseModule;
import com.garywzh.demoapp.ui.homelist.HomeModule;
import com.garywzh.demoapp.ui.homelist.MainActivity;
import com.garywzh.demoapp.network.NetworkModule;
import com.garywzh.demoapp.preference.PrefModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AppModule.class,
    NetworkModule.class,
    DataBaseModule.class,
    PrefModule.class,
    HomeModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
