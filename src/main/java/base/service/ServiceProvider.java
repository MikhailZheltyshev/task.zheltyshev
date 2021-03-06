package base.service;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static utils.PropertyReader.getAppBaseUrl;
import static utils.PropertyReader.getAppPort;

public class ServiceProvider {

    public static final String API_BASE_URL = String.format("%s:%s", getAppBaseUrl(), getAppPort());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        String authToken = Credentials.basic(username, password);
        return createService(serviceClass, authToken);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        AuthorizationInterceptor interceptor = new AuthorizationInterceptor(authToken);
        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}