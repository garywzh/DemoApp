package com.garywzh.demoapp.network;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by garywzh on 2017/4/14.
 */

@GsonTypeAdapterFactory
abstract class AutoValueGsonTypeAdapterFactory implements TypeAdapterFactory {

    public static TypeAdapterFactory create() {
        return new AutoValueGson_AutoValueGsonTypeAdapterFactory();
    }
}

