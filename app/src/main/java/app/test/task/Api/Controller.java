package app.test.task.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    private static final String BASE_URL = "https://api.github.com";
    private static Retrofit retrofit = null;

    public static ApiInterface getApi() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build()).build();
        }
        return retrofit.create(ApiInterface.class);
    }
}

