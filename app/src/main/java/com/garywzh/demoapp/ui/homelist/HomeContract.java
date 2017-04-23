package com.garywzh.demoapp.ui.homelist;

import com.garywzh.demoapp.model.Repo;
import com.garywzh.demoapp.ui.BaseContract.BasePresenter;
import com.garywzh.demoapp.ui.BaseContract.BaseView;
import java.util.List;

/**
 * Created by garywzh on 2017/4/12.
 */

interface HomeContract {

    interface HomeView extends BaseView<List<Repo>> {

    }

    interface HomePresenter extends BasePresenter<HomeView> {
        void onUsernameChanged(String username);
    }
}
