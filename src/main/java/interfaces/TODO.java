package interfaces;

import models.ResponseStatus;
import models.ToDo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TODO {

    @POST("/api/create")
    @Headers("Content-Type:application/json")
    Call<ResponseStatus> createTask(@Body ToDo task);

    @POST("/api/remove")
    @Headers("Content-Type:application/json")
    Call<ResponseStatus> removeTask(@Body ToDo task);

    @GET("/api/getAll")
    Call<ResponseBody> getToDoList();
}
