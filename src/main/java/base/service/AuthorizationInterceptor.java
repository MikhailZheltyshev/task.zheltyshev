package base.service;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {

    private String authToken;

    public AuthorizationInterceptor(String token) {
        this.authToken = token;
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Cookie", String.format("Authorization= %s", authToken));

        Request request = builder.build();
        return chain.proceed(request);
    }
}
