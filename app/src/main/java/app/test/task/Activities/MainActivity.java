package app.test.task.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.test.task.Adapter.UsersAdapter;
import app.test.task.Interface.IOnItemClickListener;
import app.test.task.Models.UserModel;
import app.test.task.R;
import app.test.task.ViewModels.UsersViewModel;

public class MainActivity extends AppCompatActivity {
    private UsersViewModel mUserViewModel;
    private List<UserModel> mUserModels = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean mFirstLoad = true;


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.users_title));
        init();
        loadUsers();
    }

    private void init() {
        mUserViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.rvUsers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UsersAdapter(mUserModels, new IOnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View transitionImage) {
                openDetailsView(mAdapter.getModelByPosition(position), transitionImage);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mRecyclerView.canScrollVertically(1)) {
                    if (mFirstLoad) {
                        getNextUsers();
                    } else {
                        mUserViewModel.getNextUsers(mAdapter.geLastId());
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserViewModel.getUsers();
            }
        });
    }

    private void loadUsers() {
        mSwipeRefreshLayout.setRefreshing(true);
        if (mUserViewModel != null) {
            mUserViewModel.getUsers().observe(this, new Observer<List<UserModel>>() {
                @Override
                public void onChanged(final List<UserModel> userModels) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (userModels == null) {
                        return;
                    }
                    mUserModels.clear();
                    mUserModels.addAll(userModels);
                    mAdapter.addNewUsers(mUserModels, true);
                    updateAdapter();
                }
            });
        }
    }

    private void getNextUsers() {
        mFirstLoad = false;
        mUserViewModel.getNextUsers(mAdapter.geLastId()).observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(final List<UserModel> userModels) {
                mAdapter.addNewUsers(userModels, false);
                updateAdapter();
            }
        });
    }

    private void updateAdapter() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void openDetailsView(UserModel model, View transitionImage) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("userAvatarUrl", model.getAvatarUrl());
        intent.putExtra("userLoginText", model.getLogin());
        intent.putExtra("webLink", model.getHtmlUrl());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this, Pair.create(transitionImage, "imageTransition")
        );
        startActivity(intent, options.toBundle());
    }
}
