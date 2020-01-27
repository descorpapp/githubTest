package app.test.task.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import app.test.task.Api.Repository.UsersRepository;
import app.test.task.Models.UserModel;

public class UsersViewModel extends AndroidViewModel {
    private UsersRepository mUsersRepository;

    public UsersViewModel(Application application) {
        super(application);
        mUsersRepository = new UsersRepository();
    }

    public LiveData<List<UserModel>> getUsers() {
        return mUsersRepository.getUsersLiveData();
    }

    public LiveData<List<UserModel>> getNextUsers(int id) {
        return mUsersRepository.getNextUsersLiveData(id);
    }
}
