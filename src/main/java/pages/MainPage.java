package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import utils.PageUtils;
import utils.WebElementsUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.WebElementsUtils.*;

public class MainPage extends BasePage {

    public static final String URL = baseUrl + "/main";

    @FindBy(tagName = "header")
    private WebElement header;

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[text()='Список дел']")
    private WebElement tasksListTitle;

    @FindBy(xpath = "//div[text()='Управление']")
    private WebElement taskInputTitle;

    @FindBy(css = ".create-form input")
    private WebElement taskInputField;

    @FindBy(css = ".create-form button")
    private WebElement addTaskButton;

    @FindBy(className = "single-todo")
    private List<WebElement> tasksRows;

    @FindBy(css = ".single-todo .todo-description")
    private List<WebElement> tasksDescriptions;

    @FindBy(css = ".single-todo .todo-index")
    private List<WebElement> tasksIndexes;

    @FindBy(css = ".single-todo .icon-button")
    private List<WebElement> taskRemoveButtons;

    private static final String FIRST_REMOVE_TASK_BUTTON_XPATH = "//button[@class='icon-button red-icon'][1]";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnLogOutButton() {
        logoutButton.click();
    }

    public void clickOnAddTaskButton() {
        addTaskButton.click();
    }

    public void sendKeysToTaskInputField(String taskText) {
        taskInputField.sendKeys(taskText);
    }

    public void clearTaskInputField() {
        taskInputField.clear();
    }

    public void addTask(String taskText) {
        taskInputField.clear();
        taskInputField.sendKeys(taskText);
        addTaskButton.click();
    }

    public void addTasks(List<String> tasks) {
        tasks.forEach(task -> addTask(task));
    }

    public int getNumberOfTasks() {
        return tasksRows.size();
    }

    public List<String> getAllTasksDescriptions() {
        return collectWebElementsLabels(tasksDescriptions);
    }

    public List<String> getAllTasksIndexes() {
        return collectWebElementsLabels(tasksDescriptions);
    }

    public void clearTasksList() {
        final int numberOfTasks = tasksRows.size();
        for (int i = 0; i < numberOfTasks; i++) {
            WebElement firstTaskRemoveButton = driver.findElement(By.xpath(FIRST_REMOVE_TASK_BUTTON_XPATH));
            firstTaskRemoveButton.click();
            waitForWebElementInvisibility(firstTaskRemoveButton, driver);
        }
    }

    public void checkLogOutButtonIsDisplayed() {
        assertThat(logoutButton.isDisplayed())
                .as("Logout button should be displayed")
                .isTrue();
    }

    public void checkLogOutButtonLabelEqualsTo(String expectedText) {
        assertThat(logoutButton.getText())
                .as("Logout button should has [{}] label", expectedText)
                .isEqualTo(expectedText);
    }

    public void checkHeaderPanelIsDisplayed() {
        assertThat(header.isDisplayed())
                .as("Header panel should be displayed")
                .isTrue();
    }

    public void checkTasksListTitleIsDisplayed() {
        assertThat(tasksListTitle.isDisplayed())
                .as("Tasks list title should be displayed")
                .isTrue();
    }

    public void checkTasksListTitleEqualsTo(String expectedText) {
        assertThat(tasksListTitle.getText())
                .as("Tasks list title should be equal to [{}]", expectedText)
                .isEqualTo(expectedText);
    }

    public void checkTaskInputFieldIsDisplayed() {
        assertThat(taskInputField.isDisplayed())
                .as("Task input field should be displayed")
                .isTrue();
    }

    public void checkAddTaskButtonIsDisplayed() {
        assertThat(addTaskButton.isDisplayed())
                .as("Add task button should be displayed")
                .isTrue();
    }

    public void checkThatNumberOfTasksIsEqualTo(int expectedNumber) {
        assertThat(getNumberOfTasks())
                .as("Number of tasks should be equal to {}", expectedNumber)
                .isEqualTo(expectedNumber);
    }

    public void checkTasksDescriptionsEqualToExpected(List<String> expectedTasks) {
        assertThat(getAllTasksDescriptions())
                .as("List of tasks in ToDo list should be equal to expected")
                .isEqualTo(expectedTasks);
    }
}
