package app.test.task.Api;

import java.util.List;

import app.test.task.Models.UserDetailsModel;
import app.test.task.Models.UserModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/users")
    Call<List<UserModel>> getUsers();

    @GET("/users")
    Call<List<UserModel>> getNextUsers(@Query("since") int id);

    @GET("/users/{login}")
    Call<UserDetailsModel> getDetails(@Path("login") String login);
}
