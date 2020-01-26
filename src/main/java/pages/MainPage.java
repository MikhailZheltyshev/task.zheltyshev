package pages;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;
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

    @FindBy(css = ".todo-description")
    private List<WebElement> tasksDescriptions;

    @FindBy(css = ".todo-index")
    private List<WebElement> tasksIndexes;

    @FindBy(css = ".single-todo .icon-button")
    private List<WebElement> taskRemoveButtons;

    private static final String FIRST_REMOVE_TASK_BUTTON_XPATH = "//button[@class='icon-button red-icon'][1]";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on Log Out button")
    public void clickOnLogOutButton() {
        logoutButton.click();
    }

    @Step("CLick on Add Task button")
    public void clickOnAddTaskButton() {
        addTaskButton.click();
    }

    @Step("Type \"{0}\" to the ToDo task input field")
    public void sendKeysToTaskInputField(String taskText) {
        taskInputField.sendKeys(taskText);
    }

    @Step("Clear the ToDo task input field")
    public void clearTaskInputField() {
        taskInputField.clear();
    }

    @Step("Add \"{0}\" ToDo task")
    public void addTask(String taskText) {
        taskInputField.clear();
        taskInputField.sendKeys(taskText);
        addTaskButton.click();
    }

    public void addTasks(List<String> tasks) {
        tasks.forEach(this::addTask);
    }

    public int getNumberOfTasks() {
        return tasksRows.size();
    }

    @Step("Get all ToDo tasks descriptions")
    public List<String> getAllTasksDescriptions() {
        return collectWebElementsLabels(tasksDescriptions);
    }

    @Step("Get all ToDo tasks indexes")
    public List<String> getAllTasksIndexes() {
        return collectWebElementsLabels(tasksIndexes);
    }

    @Step("Clear ToDo tasks list")
    public void clearTasksList() {
        if (driver.getCurrentUrl().equals(URL)) {
            final int numberOfTasks = tasksRows.size();
            for (int i = 0; i < numberOfTasks; i++) {
                final WebElement firstTaskRemoveButton = driver.findElement(By.xpath(FIRST_REMOVE_TASK_BUTTON_XPATH));
                firstTaskRemoveButton.click();
                waitForWebElementInvisibility(firstTaskRemoveButton, driver);
            }
        }
    }

    @Step("Check that Log Out button is displayed")
    public void checkLogOutButtonIsDisplayed() {
        assertThat(logoutButton.isDisplayed())
                .as("Logout button should be displayed")
                .isTrue();
    }

    @Step("Check that Log Out button has \"{0}\" as label")
    public void checkLogOutButtonLabelEqualsTo(String expectedText) {
        assertThat(logoutButton.getText())
                .as("Logout button should has label", expectedText)
                .isEqualTo(expectedText);
    }

    @Step("Check that header panel is displayed")
    public void checkHeaderPanelIsDisplayed() {
        assertThat(header.isDisplayed())
                .as("Header panel should be displayed")
                .isTrue();
    }

    @Step("Check that task input title is displayed")
    public void checkTaskInputTitleIsDisplayed() {
        assertThat(taskInputTitle.isDisplayed())
                .as("Task input title should be displayed")
                .isTrue();
    }

    @Step("Check that task input title has \"{0}\" text")
    public void checkTaskInputTitleEqualsTo(String expectedText) {
        assertThat(taskInputTitle.getText())
                .as("Task input title should be equal to ", expectedText)
                .isEqualTo(expectedText);
    }

    @Step("Check that tasks list title is displayed")
    public void checkTasksListTitleIsDisplayed() {
        assertThat(tasksListTitle.isDisplayed())
                .as("Tasks list title should be displayed")
                .isTrue();
    }

    @Step("Check that tasks list title has \"{0}\" text")
    public void checkTasksListTitleEqualsTo(String expectedText) {
        assertThat(tasksListTitle.getText())
                .as("Tasks list title should be equal to ", expectedText)
                .isEqualTo(expectedText);
    }

    @Step("Check that task input field is displayed")
    public void checkTaskInputFieldIsDisplayed() {
        assertThat(taskInputField.isDisplayed())
                .as("Task input field should be displayed")
                .isTrue();
    }

    @Step("Check that add task button is displayed")
    public void checkAddTaskButtonIsDisplayed() {
        assertThat(addTaskButton.isDisplayed())
                .as("Add task button should be displayed")
                .isTrue();
    }

    @Step("Check that Add task button has \"{0}\" text")
    public void checkAddTaskButtonHasExpectedLabel(String expectedLabel) {
        final String addTaskButtonOwnText = getWebElementTextExcludingChildren(addTaskButton);
        assertThat(addTaskButtonOwnText)
                .as("Add task button has", expectedLabel)
                .isEqualTo(expectedLabel);
    }

    @Step("Check that number of tasks is equal to {0}")
    public void checkNumberOfTasksIsEqualTo(int expectedNumber) {
        assertThat(getNumberOfTasks())
                .as("Number of tasks should be equal to", expectedNumber)
                .isEqualTo(expectedNumber);
    }

    @Step("Check that task descriptions are equal to expected")
    public void checkTasksDescriptionsEqualToExpected(List<String> expectedTasks) {
        List<String> allTasksDescriptions = getAllTasksDescriptions();
        assertThat(allTasksDescriptions)
                .as("List of tasks in ToDo list should be equal to expected")
                .isEqualTo(expectedTasks);
    }

    @Step("Check that task numeration is correct")
    public void checkTasksNumerationIsCorrect(List<String> expectedTasks) {
        final List<String> actualIndexes = getAllTasksIndexes();
        final List<String> actualDescriptions = getAllTasksDescriptions();
        final Map<String, String> expectedIndexedTasks = IntStream.range(0, expectedTasks.size())
                .boxed()
                .collect(toMap(i -> String.valueOf(i + 1), expectedTasks::get));
        final Map<String, String> actualIndexedTasks = IntStream.range(0, actualDescriptions.size())
                .boxed()
                .collect(toMap(actualIndexes::get, actualDescriptions::get));
        assertThat(actualIndexedTasks)
                .as("Tasks in ToDo list should be indexed correctly")
                .isEqualTo(expectedIndexedTasks);
    }

    @Step("Check that each task has remove button")
    public void checkEachTaskHasRemoveButton() {
        final SoftAssertions softAssertions = new SoftAssertions();
        tasksRows.forEach(row -> softAssertions.assertThat(row.findElement(By.cssSelector(".icon-button")).isDisplayed())
                .as("Remove button should be displayed for {} row", row.getText())
                .isTrue());
        softAssertions.assertAll();
    }
}
