package com.garywzh.demoapp.model;

import android.support.annotation.NonNull;
import com.garywzh.RepoModel;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.RowMapper;

/**
 * Created by garywzh on 2017/4/12.
 */

@AutoValue
public abstract class Repo implements RepoModel {

    public static TypeAdapter<Repo> typeAdapter(Gson gson) {
        return new AutoValue_Repo.GsonTypeAdapter(gson);
    }

    public static final Factory<Repo> FACTORY = new Factory<>(new Creator<Repo>() {
        @Override
        public Repo create(long id, @NonNull String name) {
            return new AutoValue_Repo(id, name);
        }
    });

    public static final RowMapper<Repo> SELECT_ALL_MAPPER = FACTORY.select_allMapper();
}
