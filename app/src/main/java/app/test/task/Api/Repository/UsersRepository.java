package app.test.task.Api.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import app.test.task.Api.Controller;
import app.test.task.App;
import app.test.task.Models.UserModel;
import app.test.task.R;
import app.test.task.Utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.test.task.Utils.Util.handleForbiddenState;

public class UsersRepository {
    private MutableLiveData<List<UserModel>> mUsersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserModel>> mUsersNextLiveData = new MutableLiveData<>();

    public UsersRepository() {
    }

    public MutableLiveData<List<UserModel>> getUsersLiveData() {
        Controller.getApi().getUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    mUsersLiveData.postValue(response.body());
                } else {
                    mUsersLiveData.postValue(null);
                    handleForbiddenState(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Util.showErrorMsg(App.getContext().getResources().getString(R.string.error_message));
                mUsersLiveData.postValue(null);
            }
        });
        return mUsersLiveData;
    }

    public MutableLiveData<List<UserModel>> getNextUsersLiveData(int id) {
        Controller.getApi().getNextUsers(id).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    mUsersNextLiveData.postValue(response.body());
                } else {
                    handleForbiddenState(response.code());
                    mUsersNextLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Util.showErrorMsg(App.getContext().getResources().getString(R.string.error_message));
                mUsersNextLiveData.postValue(null);
            }
        });
        return mUsersNextLiveData;
    }
}
