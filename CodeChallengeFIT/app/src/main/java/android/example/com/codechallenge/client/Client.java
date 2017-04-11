package android.example.com.codechallenge.client;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Client {

    public static final String API_BASE_URL = "http://petstore.swagger.io/";

    private Retrofit clientInstance;

    private ClientService clientService;

    public interface ClientService {
        @GET("/v2/pet/findByStatus?status=available")
        Call<List<Pet>> getPets();


        @GET("/v2/pet/{contactId}")
        Call<Pet> getDetail(@Path("contactId") Long contactId);

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

    public Call<List<Pet>> getPets() {
        return clientService.getPets();
    }

    public Call<Pet> getDetails(Long id) {
        return clientService.getDetail(id);
    }

}
