package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

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
}
