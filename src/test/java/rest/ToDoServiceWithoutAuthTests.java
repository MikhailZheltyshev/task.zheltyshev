package rest;

import okhttp3.ResponseBody;
import org.testng.annotations.Test;
import retrofit2.Response;
import services.ToDoService;

import static constants.HttpStatusCodes.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.StringsGenerator.getRandomString;

public class ToDoServiceWithoutAuthTests {

    private ToDoService toDoService;

    @Test(description = "Check that 401 Unauthorized status code is returned on attempt to request ToDo list without auth token")
    public void checkRequestForToDoListWithoutAuthTokenReturnsNotAuthorizedHttpStatusCode() {
        toDoService = new ToDoService();
        Response<ResponseBody> toDoListResponse = toDoService.requestToDoList();
        int toDoListResponseCode = toDoListResponse.code();
        assertThat(toDoListResponseCode)
                .as("401 UNAUTHORIZED Http status should be returned on attempt to get ToDo list without auth token")
                .isEqualTo(UNAUTHORIZED);
    }

    @Test(description = "Check that 401 Unauthorized status code is returned on attempt to request ToDo list with invalid auth token")
    public void checkRequestForToDoListWithInvalidAuthTokenReturnsNotAuthorizedHttpStatusCode() {
        toDoService = new ToDoService(getRandomString(10));
        Response<ResponseBody> toDoListResponse = toDoService.requestToDoList();
        int toDoListResponseCode = toDoListResponse.code();
        assertThat(toDoListResponseCode)
                .as("401 UNAUTHORIZED Http status should be returned on attempt to get ToDo list with invalid auth token")
                .isEqualTo(UNAUTHORIZED);
    }
}
