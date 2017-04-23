package com.garywzh.demoapp.ui.homelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.garywzh.demoapp.R;
import com.garywzh.demoapp.model.Repo;
import java.util.List;

/**
 * Created by garywzh on 2017/4/12.
 */

class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int TYPE_REPO = 0x01;
    private List<Repo> repos;

    void setData(List<Repo> repos) {
        this.repos = repos;
    }

    @Override
    public int getItemCount() {
        return repos == null ? 0 : repos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_REPO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REPO) {
            final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_list_item, parent, false);
            return new RepoViewHolder(view);
        } else {
            throw new RuntimeException("wrong view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepoViewHolder) {
            ((RepoViewHolder) holder).fillData(repos.get(position));
        }
    }

    @Override
    public long getItemId(int position) {
        return repos.get(position).id();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id)
        TextView idView;

        @BindView(R.id.name)
        TextView nameView;

        RepoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void fillData(Repo repo) {
            idView.setText(String.valueOf(repo.id()));
            nameView.setText(repo.name());
        }
    }
}