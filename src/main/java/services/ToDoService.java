package services;

import base.service.ServiceProvider;
import interfaces.TODO;
import io.qameta.allure.Step;
import models.ResponseStatus;
import models.ToDo;
import models.ToDoList;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static utils.ApiResponseUtils.convertResponseBodyToType;

public class ToDoService {

    private TODO toDoInterface;

    public ToDoService() {
        this.toDoInterface = ServiceProvider.createService(TODO.class);
    }

    public ToDoService(String authToken) {
        this.toDoInterface = ServiceProvider.createService(TODO.class, authToken);
    }

    @Step("Perform request to ToDo service for ToDo list")
    public Response<ResponseBody> requestToDoList() {
        try {
            return toDoInterface.getToDoList().execute();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get all tasks list", e);
        }
    }

    @Step("Perform request to ToDo service to remove task with \"{0}\" id")
    public Response<ResponseStatus> removeTask(int taskId) {
        ToDo task = new ToDo(taskId, null, null);
        try {
            return toDoInterface.removeTask(task).execute();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to remove task", e);
        }
    }

    @Step("Perform request to ToDo service to create task with \"{0}\" description")
    public Response<ResponseStatus> createTask(String taskDescription) {
        ToDo task = new ToDo(null, null, taskDescription);
        try {
            return toDoInterface.createTask(task).execute();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create task", e);
        }
    }

    public void addListOfTasks(List<String> descriptions) {
        descriptions.forEach(this::createTask);
    }

    @Step("Collecting all ToDo tasks descriptions")
    public List<String> collectAllTasksDescriptions() {
        ToDoList toDoList = convertResponseBodyToType(requestToDoList().body(), ToDoList.class);
        return toDoList.getTodoList().stream()
                .map(ToDo::getDescription)
                .collect(toList());
    }

    public void removeAllTasks() {
        ResponseBody body = requestToDoList().body();
        ToDoList toDoList = convertResponseBodyToType(body, ToDoList.class);
        toDoList.getTodoList().stream()
                .map(ToDo::getId)
                .forEach(id -> removeTask(id));
    }
}
