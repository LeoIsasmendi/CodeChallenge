package android.example.com.codechallenge003.network;

import android.example.com.codechallenge003.BuildConfig;
import android.example.com.codechallenge003.model.PopularTvShows;
import android.example.com.codechallenge003.model.TvShowCredits;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Client {

    private static final String API_BASE_URL = "https://api.themoviedb.org/";

    private Retrofit clientInstance;

    private ClientService clientService;

    public interface ClientService {
        @GET("/3/tv/popular?api_key=" + BuildConfig.API_KEY + "&language=en-US&page=1")
        Call<PopularTvShows> getPopularTvShows();

        @GET("/3/tv/{showId}/credits?api_key=" + BuildConfig.API_KEY + "&language=en-US&page=1")
        Call<TvShowCredits> getTvShowCredits(@Path("showId") Integer contactId);

    }

    public Client() {
        //Constructor
    }

    public Client build() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Initialize RestClient with Rest interceptor
        OkHttpClient.Builder asClient = new OkHttpClient.Builder().addInterceptor(logging);


        // Initiliaze Retrofit with Jackson as a default converter, and Rest Interceptor
        clientInstance = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(asClient.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Initialize Retrofit RestService
        clientService = clientInstance.create(ClientService.class);
        return this;
    }

    public Call<PopularTvShows> getPopularTvShows() {
        return clientService.getPopularTvShows();
    }

    public Call<TvShowCredits> getTvShowCredits(Integer id) {
        return clientService.getTvShowCredits(id);
    }
}