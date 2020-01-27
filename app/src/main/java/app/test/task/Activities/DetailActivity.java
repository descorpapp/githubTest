package app.test.task.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import java.util.Objects;

import app.test.task.App;
import app.test.task.Models.UserDetailsModel;
import app.test.task.R;
import app.test.task.ViewModels.DetailsViewModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView tvUserAvatar;
    private UserDetailsModel mUserDetailsModel = new UserDetailsModel();
    private DetailsViewModel mDetailsViewModel;
    private TextView tvUserLogin, tvGists, tvRepos, tvFollowers, tvWeblink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        addBackButton();

        mUserDetailsModel.setAvatarUrl(Objects.requireNonNull(getIntent().getExtras()).getString("userAvatarUrl"));
        mUserDetailsModel.setLogin(getIntent().getExtras().getString("userLoginText"));
        mUserDetailsModel.setHtmlUrl(getIntent().getExtras().getString("webLink"));
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        init();
        fillInfo();
        getUserDetails();
        setPreloadedInfo();
    }

    private void setPreloadedInfo() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(mUserDetailsModel.getLogin());
        tvWeblink.setText(mUserDetailsModel.getHtmlUrl());
        tvUserLogin.setText(mUserDetailsModel.getLogin());
    }

    private void init() {
        tvUserAvatar = findViewById(R.id.tvUserAvatar);
        tvUserLogin = findViewById(R.id.tvUserLogin);
        tvGists = findViewById(R.id.tvGists);
        tvRepos = findViewById(R.id.tvRepos);
        tvFollowers = findViewById(R.id.tvFollowers);
        tvWeblink = findViewById(R.id.tvWeblink);
        tvWeblink.setOnClickListener(this);
    }

    private void fillInfo() {
        tvUserLogin.setText(mUserDetailsModel.getLogin());
        tvGists.setText(formattedString(mUserDetailsModel.getGists(), R.string.gists_text));
        tvFollowers.setText(formattedString(mUserDetailsModel.getFollowers(), R.string.followers_text));
        tvRepos.setText(formattedString(mUserDetailsModel.getRepos(), R.string.repos_text));
        Objects.requireNonNull(getSupportActionBar()).setTitle(mUserDetailsModel.getName());
    }

    private String formattedString(int value, int id) {
        return String.format(getResources().getString(id), Objects.toString(value, "0"));
    }

    private void getUserDetails() {
        Glide.with(App.getContext()).load(mUserDetailsModel.getAvatarUrl()).placeholder(R.drawable.default_user_avatar).centerCrop().into(tvUserAvatar);
        if (mDetailsViewModel != null) {
            mDetailsViewModel.getDetails(mUserDetailsModel.getLogin()).observe(this, new Observer<UserDetailsModel>() {
                @Override
                public void onChanged(UserDetailsModel userDetailsModel) {
                    mUserDetailsModel = userDetailsModel;
                    fillInfo();
                }
            });
        }
    }

    private void addBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvWeblink) {
            openWebLink(mUserDetailsModel.getHtmlUrl());
        }
    }

    private void openWebLink(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
