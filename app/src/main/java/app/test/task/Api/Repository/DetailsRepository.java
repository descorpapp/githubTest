package app.test.task.Api.Repository;

import androidx.lifecycle.MutableLiveData;

import app.test.task.Api.Controller;
import app.test.task.App;
import app.test.task.Models.UserDetailsModel;
import app.test.task.R;
import app.test.task.Utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.test.task.Utils.Util.handleForbiddenState;

public class DetailsRepository {
    private MutableLiveData<UserDetailsModel> mDetailsLiveData = new MutableLiveData<>();

    public DetailsRepository() {
    }

    public MutableLiveData<UserDetailsModel> getDetailsLiveData(String login) {
        Controller.getApi().getDetails(login).enqueue(new Callback<UserDetailsModel>() {
            @Override
            public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                if (response.isSuccessful()) {
                    mDetailsLiveData.postValue(response.body());
                } else {
                    mDetailsLiveData.postValue(null);
                    handleForbiddenState(response.code());
                }
            }

            @Override
            public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                Util.showErrorMsg(App.getContext().getResources().getString(R.string.error_message));
            }
        });
        return mDetailsLiveData;
    }
}
