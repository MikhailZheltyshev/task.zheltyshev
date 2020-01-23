package pages;

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
import static utils.WebElementsUtils.collectWebElementsLabels;
import static utils.WebElementsUtils.waitForWebElementInvisibility;

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
        return collectWebElementsLabels(tasksIndexes);
    }

    public void clearTasksList() {
        if (driver.getCurrentUrl().equals(URL)) {
            final int numberOfTasks = tasksRows.size();
            for (int i = 0; i < numberOfTasks; i++) {
                WebElement firstTaskRemoveButton = driver.findElement(By.xpath(FIRST_REMOVE_TASK_BUTTON_XPATH));
                firstTaskRemoveButton.click();
                waitForWebElementInvisibility(firstTaskRemoveButton, driver);
            }
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

    public void checkTaskInputTitleIsDisplayed() {
        assertThat(taskInputTitle.isDisplayed())
                .as("Task input title should be displayed")
                .isTrue();
    }

    public void checkTaskInputTitleEqualsTo(String expectedText) {
        assertThat(taskInputTitle.getText())
                .as("Task input title should be equal to [{}]", expectedText)
                .isEqualTo(expectedText);
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

    public void checkAddTaskButtonHasExpectedLabel(String expectedLabel) {
        assertThat(addTaskButton.getText())
                .as("Add task button has [{}]", expectedLabel)
                .isEqualTo(expectedLabel);
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

    public void checkTasksNumerationIsCorrect(List<String> expectedTasks) {
        List<String> actualIndexes = getAllTasksIndexes();
        List<String> actualDescriptions = getAllTasksDescriptions();
        Map<String, String> expectedIndexedTasks = IntStream.range(0, expectedTasks.size())
                .boxed()
                .collect(toMap(i -> String.valueOf(i + 1), expectedTasks::get));
        Map<String, String> actualIndexedTasks = IntStream.range(0, actualDescriptions.size())
                .boxed()
                .collect(toMap(actualIndexes::get, actualDescriptions::get));
        assertThat(actualIndexedTasks)
                .as("Tasks in ToDo list should be indexed correctly")
                .isEqualTo(expectedIndexedTasks);
    }

    public void checkEachTaskHasRemoveButton() {
        SoftAssertions softAssertions = new SoftAssertions();
        tasksRows.forEach(row -> {
            softAssertions.assertThat(row.findElement(By.cssSelector(".icon-button")).isDisplayed())
                    .as("Remove button should be displayed for {} row", row.getText())
                    .isTrue();
        });
        softAssertions.assertAll();
    }
}
