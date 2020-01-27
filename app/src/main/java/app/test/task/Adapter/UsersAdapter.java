package app.test.task.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.test.task.App;
import app.test.task.Interface.IOnItemClickListener;
import app.test.task.Models.UserModel;
import app.test.task.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.Holder> {
    private IOnItemClickListener onItemClickListener;
    private ArrayList<UserModel> mUsers = new ArrayList<>();

    public UsersAdapter(List<UserModel> _users, IOnItemClickListener _onItemClickListener) {
        mUsers.addAll(_users);
        onItemClickListener = _onItemClickListener;
    }

    public void addNewUsers(List<UserModel> newUsers, boolean refresh) {
        if (newUsers != null) {
            if (refresh) {
                mUsers.clear();
            }
            mUsers.addAll(mUsers.size(), newUsers);
        }
        Log.e("TAG", "add new users size " + mUsers.size());
    }

    public UserModel getModelByPosition(int pos) {
        return mUsers.get(pos);
    }

    @NonNull
    @Override
    public UsersAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersAdapter.Holder holder, final int position) {
        holder.tvName.setText(mUsers.get(position).getLogin());
        Glide.with(App.getContext()).load(mUsers.get(position).getAvatarUrl()).placeholder(R.drawable.default_user_avatar).centerCrop().into(holder.ivUserAvatar);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(position, holder.ivUserAvatar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public int geLastId() {
        return mUsers.get(mUsers.size() - 1).getId();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvName;
        CircleImageView ivUserAvatar;
        CardView cardView;

        Holder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.userName);
            ivUserAvatar = itemView.findViewById(R.id.userAvatar);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
