package interfaces;

import models.LoginResponseStatus;
import models.UserCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Authorization {

    @POST("/api/login")
    @Headers("Content-Type:application/json")
    public Call<LoginResponseStatus> login(@Body UserCredentials credentials);

    @POST
    @Headers("Content-Type:application/json")
    public Call<LoginResponseStatus> logout(@Body UserCredentials credentials);
}
