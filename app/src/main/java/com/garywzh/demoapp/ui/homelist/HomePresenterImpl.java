package com.garywzh.demoapp.ui.homelist;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

import android.database.Cursor;
import com.garywzh.demoapp.model.Repo;
import com.garywzh.demoapp.network.GithubApi;
import com.garywzh.demoapp.preference.StringPreference;
import com.garywzh.demoapp.ui.homelist.HomeContract.HomeView;
import com.garywzh.demoapp.util.LogUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.BriteDatabase.Transaction;
import com.squareup.sqlbrite.SqlBrite.Query;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garywzh on 2017/4/12.
 */

class HomePresenterImpl implements HomeContract.HomePresenter {

    public static final String TAG = HomePresenterImpl.class.getSimpleName();

    private GithubApi githubApi;
    private BriteDatabase database;
    private StringPreference usernamePref;

    private HomeView view;
    private Disposable disposable;

    public HomePresenterImpl(GithubApi githubApi, BriteDatabase database,
        StringPreference username) {
        this.githubApi = githubApi;
        this.database = database;
        this.usernamePref = username;
    }

    @Override
    public void onStart(HomeView view) {
        LogUtils.d(TAG, "onStart called --------------------");

        this.view = view;
        loadContent();
    }

    @Override
    public void onStop() {
        LogUtils.d(TAG, "onStop called --------------------");

        disposable.dispose();
        view = null;
    }

    @Override
    public void loadContent() {
        LogUtils.d(TAG, "loading data from db");

        disposable = RxJavaInterop.toV2Observable(
            database.createQuery(Repo.TABLE_NAME, Repo.FACTORY.select_all().statement))
            .map(new Function<Query, List<Repo>>() {
                @Override
                public List<Repo> apply(@NonNull Query query) throws Exception {
                    List<Repo> repos = new ArrayList<>();
                    Cursor cursor = query.run();
                    while (cursor.moveToNext()) {
                        repos.add(Repo.SELECT_ALL_MAPPER.map(cursor));
                    }
                    LogUtils.d(TAG, "db repos size: " + repos.size());
                    return repos;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<List<Repo>>() {
                @Override
                public void onNext(final List<Repo> repos) {
                    if (view != null) {
                        view.showContent(repos);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (view != null) {
                        view.showError(e.getMessage());
                    }
                }

                @Override
                public void onComplete() {
                }
            });
        refreshContent();
    }

    @Override
    public void refreshContent() {
        if (view != null) {
            view.showLoading();
        }

        LogUtils.d(TAG, "loading data from network");

        githubApi.getReposByUser(usernamePref.get())
            .doOnSuccess(new Consumer<List<Repo>>() {
                @Override
                public void accept(@NonNull List<Repo> repos) throws Exception {
                    LogUtils.d(TAG, "network repos size: " + repos.size());

                    Transaction transaction = database.newTransaction();
                    try {
                        for (Repo repo : repos) {
                            database.insert(Repo.TABLE_NAME, Repo.FACTORY.marshal(repo)
                                .asContentValues(), CONFLICT_REPLACE);
                        }
                        transaction.markSuccessful();
                    } finally {
                        transaction.end();
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<List<Repo>>() {
                @Override
                public void onSuccess(@NonNull List<Repo> repos) {

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogUtils.d(TAG, e.getMessage());

                    if (view != null) {
                        view.showError(e.getMessage());
                    }
                }
            });
    }

    @Override
    public void onUsernameChanged(String username) {
        LogUtils.d(TAG, "username changed: " + username);

        usernamePref.set(username);
        database.execute(Repo.DELETE_ALL);
        refreshContent();
    }
}
