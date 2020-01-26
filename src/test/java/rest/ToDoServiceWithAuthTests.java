package rest;

import models.ResponseStatus;
import models.ToDo;
import models.ToDoList;
import models.UserCredentials;
import okhttp3.ResponseBody;
import org.testng.annotations.*;
import retrofit2.Response;
import services.AuthorizationService;
import services.ToDoService;

import java.util.List;

import static constants.HttpStatusCodes.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.ApiResponseUtils.convertResponseBodyToType;
import static utils.ListsUtils.getRandomElementFromList;
import static utils.ListsUtils.joinLists;
import static utils.StringsGenerator.*;

public class ToDoServiceWithAuthTests {

    private ToDoService toDoService;
    private AuthorizationService authorizationService;
    private String authToken;

    @Parameters({"username", "password"})
    @BeforeMethod(description = "Setting up authorized access to ToDo service")
    public void setUpAuthorized(@Optional("john_dow@some.domaine.com") String username,
                                @Optional("123456789") String password) {
        UserCredentials cred = new UserCredentials(username, password);
        this.authorizationService = new AuthorizationService();
        this.authToken = authorizationService.getAuthToken(cred);
        this.toDoService = new ToDoService(authToken);
    }

    @Test(description = "Check that 200 OK Http status code returned in response for authorized User requested ToDo list")
    public void checkAuthorizedRequestForToDoListReturnsOkHttpStatusCode() {
        Response<ResponseBody> toDoListResponse = toDoService.requestToDoList();
        int toDoListResponseCode = toDoListResponse.code();
        assertThat(toDoListResponseCode)
                .as("200 OK Http status should be returned on attempt to get ToDo list with valid auth token")
                .isEqualTo(OK);
    }

    @Test(description = "Check that ToDo list returned in response for authorized User")
    public void checkAuthorizedRequestForToDoListReturnsToDoList() {
        ResponseBody toDoListResponseBody = toDoService.requestToDoList().body();
        ToDoList toDoList = convertResponseBodyToType(toDoListResponseBody, ToDoList.class);
        assertThat(toDoList.getTodoList())
                .as("ToDo list should be returned in response for request with valid auth token")
                .isNotNull();
    }

    @Test(description = "Check that authorized User is able to create new tasks")
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

    @Test(description = "Check that 200 OK Http status is returned in response for User requested to create task with non-empty description")
    public void checkResponseForRequestToCreateNonEmptyTaskReturnsOkHttpStatus() {
        Response<ResponseStatus> responseOnTaskCreate = toDoService.createTask(getRandomString(5));
        assertThat(responseOnTaskCreate.code())
                .as("OK 200 Http status returned on request to create non empty task")
                .isEqualTo(OK);
    }

    @Test(description = "Check that 200 OK Http status is returned in response for User requested to create task with empty description")
    public void checkResponseForRequestToCreateEmptyTaskReturnsOkHttpStatus() {
        Response<ResponseStatus> responseOnTaskCreate = toDoService.createTask(EMPTY);
        assertThat(responseOnTaskCreate.code())
                .as("OK 200 Http status returned on request to create empty task")
                .isEqualTo(OK);
    }

    @Test(description = "Check that empty task can't be added to the tasks list of authorized User")
    public void checkEmptyTaskCantBeAdded() {
        List<String> initialTasksList = toDoService.collectAllTasksDescriptions();
        toDoService.createTask(EMPTY);
        List<String> actualTasksList = toDoService.collectAllTasksDescriptions();
        assertThat(actualTasksList)
                .as("Initial tasks list weren't changed after attempt to create task with empty description")
                .isEqualTo(initialTasksList);
    }

    @Test(description = "Check that task can be removed by authorized User")
    public void checkTaskCanBeRemovedByAuthorizedUser() {
        toDoService.addListOfTasks(generateListOfRandomStrings(5, 10));
        ToDoList expectedToDoList = toDoService.getToDoList();
        ToDo taskToBeRemoved = getRandomElementFromList(expectedToDoList.getTodoList());
        expectedToDoList.getTodoList().remove(taskToBeRemoved);
        toDoService.removeTask(taskToBeRemoved.getId());
        ToDoList actualToDoList = toDoService.getToDoList();
        assertThat(actualToDoList)
                .as("Check that task is being removed by authorized User")
                .isEqualTo(expectedToDoList);
    }

    @Test(description = "Check User can't access ToDo list after logout request")
    public void checkUserCantAccessToDoListAfterLogoutRequest() {
        AuthorizationService logoutService = new AuthorizationService(authToken);
        toDoService.addListOfTasks(generateListOfRandomStrings(5, 10));
        logoutService.logout();
        String responseContentType = toDoService.requestToDoList().body().contentType().toString();
        assertThat(responseContentType)
                .as("User should not retrieve ToDo list after logout using old auth token")
                .isEqualTo("text/html;charset=UTF-8");
    }

    @Parameters({"username", "password"})
    @AfterMethod(description = "Clean up tasks after test")
    public void cleanUp(@Optional("john_dow@some.domaine.com") String username,
                        @Optional("123456789") String password) {
        UserCredentials cred = new UserCredentials(username, password);
        new ToDoService(authorizationService.getAuthToken(cred)).removeAllTasks();
    }
}
