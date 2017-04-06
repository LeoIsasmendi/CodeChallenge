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

    public static final String API_BASE_URL = "https://private-d0cc1-iguanafixtest.apiary-mock.com/";

    private Retrofit clientInstance;

    private ClientService clientService;

    public interface ClientService {
        @GET("/contacts")
        Call<List<Contact>> getContact();


        @GET("contacts/{contactId}")
        Call<Detail> getDetail(@Path("contactId") Long contactId);

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

    public Call<List<Contact>> fetchContacts() {
        return clientService.getContact();
    }

    public Call<Detail> fetchDetails(Long id) {
        return clientService.getDetail(id);
    }

}
