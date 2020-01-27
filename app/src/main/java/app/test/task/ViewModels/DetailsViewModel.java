package app.test.task.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import app.test.task.Api.Repository.DetailsRepository;
import app.test.task.Models.UserDetailsModel;

public class DetailsViewModel extends AndroidViewModel {
    private DetailsRepository mDetailsRepository;

    public DetailsViewModel(Application application) {
        super(application);
        mDetailsRepository = new DetailsRepository();
    }

    public LiveData<UserDetailsModel> getDetails(String login) {
        return mDetailsRepository.getDetailsLiveData(login);
    }
}
