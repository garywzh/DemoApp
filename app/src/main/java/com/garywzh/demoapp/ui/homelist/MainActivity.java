package com.garywzh.demoapp.ui.homelist;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.garywzh.demoapp.DemoApplication;
import com.garywzh.demoapp.R;
import com.garywzh.demoapp.model.Repo;
import com.garywzh.demoapp.preference.StringPreference;
import com.garywzh.demoapp.preference.Username;
import com.garywzh.demoapp.ui.BaseActivity;
import com.garywzh.demoapp.ui.homelist.HomeContract.HomePresenter;
import com.garywzh.demoapp.ui.homelist.HomeContract.HomeView;
import com.garywzh.demoapp.util.ViewUtils;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements HomeView, OnRefreshListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.edit_username)
    EditText edit_username;

    HomeAdapter adapter;
    @Inject
    HomePresenter presenter;
    @Inject
    @Username
    StringPreference usernamePref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((DemoApplication) getApplication()).injector().inject(this);
        ButterKnife.bind(this);
        edit_username.setText(usernamePref.get());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @OnClick(R.id.button_username)
    void onButtonUsernameClicked() {
        String username = edit_username.getText().toString();
        if (username.isEmpty()) {
            showError("Not Null!");
        } else {
            ViewUtils.hideInputMethod(edit_username);
            presenter.onUsernameChanged(username);
        }
    }

    @Override
    public void onRefresh() {
        presenter.refreshContent();
    }

    @Override
    public void showContent(List<Repo> repos) {
        adapter.setData(repos);
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    private void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        hideLoading();
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }
}
