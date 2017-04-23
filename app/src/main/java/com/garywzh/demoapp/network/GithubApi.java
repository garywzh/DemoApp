package com.garywzh.demoapp.network;

import com.garywzh.demoapp.model.Repo;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by garywzh on 2017/3/17.
 */

public interface GithubApi {

    @GET("users/{user}/repos")
    Single<List<Repo>> getReposByUser(@Path("user") String user);
}
