package com.garywzh.demoapp.ui;

/**
 * Created by garywzh on 2017/4/16.
 */

public interface BaseContract {

    interface BaseView<M> {

        void showContent(M model);

        void showLoading();

        void showError(String error);
    }

    interface BasePresenter<V extends BaseView> {

        void onStart(V view);

        void onStop();

        void loadContent();

        void refreshContent();
    }
}
