package rest;

import models.ResponseStatus;
import models.ToDoList;
import models.UserCredentials;
import okhttp3.ResponseBody;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;
import services.AuthorizationService;
import services.ToDoService;

import java.io.IOException;
import java.util.List;

import static constants.HttpStatusCodes.OK;
import static constants.HttpStatusCodes.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.ApiResponseUtils.convertResponseBodyToType;
import static utils.ListsUtils.joinLists;
import static utils.StringsGenerator.*;

public class ToDoServiceTests {

    private ToDoService toDoService;

    @BeforeMethod(groups = {"authorized"})
    public void setUpAuthorized() throws IOException {
        UserCredentials cred = new UserCredentials("john_dow@some.domaine.com", "123456789");
        String authToken = new AuthorizationService().getAuthToken(cred);
        this.toDoService = new ToDoService(authToken);
    }

    @Test(groups = {"authorized", "rest", "todo-service", "positive"})
    public void checkAuthorizedRequestForToDoListReturnsOkHttpStatusCode() {
        Response<ResponseBody> toDoListResponse = toDoService.requestToDoList();
        int toDoListResponseCode = toDoListResponse.code();
        assertThat(toDoListResponseCode)
                .as("200 OK Http status should be returned on attempt to get ToDo list with valid auth token")
                .isEqualTo(OK);
    }

    @Test(groups = {"rest", "todo-service", "negative"})
    public void checkRequestForToDoListWithoutAuthTokenReturnsNotAuthorizedHttpStatusCode() {
        toDoService = new ToDoService();
        Response<ResponseBody> toDoListResponse = toDoService.requestToDoList();
        int toDoListResponseCode = toDoListResponse.code();
        assertThat(toDoListResponseCode)
                .as("401 UNAUTHORIZED Http status should be returned on attempt to get ToDo list without auth token")
                .isEqualTo(UNAUTHORIZED);
    }

    @Test(groups = {"authorized", "rest", "todo-service", "negative"})
    public void checkRequestForToDoListWithInvalidAuthTokenReturnsNotAuthorizedHttpStatusCode() {
        toDoService = new ToDoService(getRandomString(10));
        Response<ResponseBody> toDoListResponse = toDoService.requestToDoList();
        int toDoListResponseCode = toDoListResponse.code();
        assertThat(toDoListResponseCode)
                .as("401 UNAUTHORIZED Http status should be returned on attempt to get ToDo list with invalid auth token")
                .isEqualTo(UNAUTHORIZED);
    }

    @Test(groups = {"authorized", "rest", "todo-service", "negative"})
    public void checkAuthorizedRequestForToDoListReturnsToDoList() {
        ResponseBody toDoListResponseBody = toDoService.requestToDoList().body();
        ToDoList toDoList = convertResponseBodyToType(toDoListResponseBody, ToDoList.class);
        assertThat(toDoList.getTodoList())
                .as("ToDo list should be returned in response for request with valid auth token")
                .isNotNull();
    }

    @Test(groups = {"authorized", "rest", "todo-service", "negative"})
    public void checkAuthorizedUserCanAddTasks() {
        List<String> initialTasksList = toDoService.collectAllTasksDescriptions();
        List<String> tasksToAdd = generateListOfRandomStrings(5, 25);
        List<String> expectedTasksList = joinLists(initialTasksList, tasksToAdd);
        toDoService.addListOfTasks(tasksToAdd);
        List<String> actualTasksList = toDoService.collectAllTasksDescriptions();
        assertThat(actualTasksList)
                .as("Tasks were successfully added by authorized user")
                .isEqualTo(expectedTasksList);
    }

    @Test(groups = {"authorized", "rest", "todo-service", "negative"})
    public void checkResponseForRequestToCreateNonEmptyTaskReturnsOkHttpStatus() {
        Response<ResponseStatus> responseOnTaskCreate = toDoService.createTask(getRandomString(5));
        assertThat(responseOnTaskCreate.code())
                .as("OK 200 Http status returned on request to create non empty task")
                .isEqualTo(OK);
    }

    @Test(groups = {"authorized", "rest", "todo-service", "negative"})
    public void checkResponseForRequestToCreateEmptyTaskReturnsOkHttpStatus() {
        Response<ResponseStatus> responseOnTaskCreate = toDoService.createTask(EMPTY);
        assertThat(responseOnTaskCreate.code())
                .as("OK 200 Http status returned on request to create empty task")
                .isEqualTo(OK);
    }

    @Test(groups = {"authorized", "rest", "todo-service", "negative"})
    public void checkEmptyTaskCantBeAdded() {
        List<String> initialTasksList = toDoService.collectAllTasksDescriptions();
        toDoService.createTask(EMPTY);
        List<String> actualTasksList = toDoService.collectAllTasksDescriptions();
        assertThat(actualTasksList)
                .as("Initial tasks list weren't changed after attempt to create task with empty description")
                .isEqualTo(initialTasksList);
    }

    @AfterMethod(groups = {"authorized"})
    public void cleanUp() {
        toDoService.removeAllTasks();
    }
}
