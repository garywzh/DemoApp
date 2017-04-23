package com.garywzh.demoapp.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.garywzh.demoapp.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by garywzh on 2017/4/5.
 */

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://api.github.com/";


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
            .registerTypeAdapterFactory(AutoValueGsonTypeAdapterFactory.create())
            .create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d("Http info: ", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    }

    @Provides
    @Singleton
    GithubApi provideGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }
}
