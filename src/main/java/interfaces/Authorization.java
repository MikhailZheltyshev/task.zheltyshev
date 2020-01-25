package interfaces;

import models.LoginResponseStatus;
import models.UserCredentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Authorization {

    @POST("/api/login")
    Call<LoginResponseStatus> login(@Body UserCredentials credentials);

    @POST("/api/logout")
    Call<ResponseBody> logout();
}
