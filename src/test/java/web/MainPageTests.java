package web;

import base.web.WebTestBase;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.MainPage;

import java.util.List;

import static pages.BasePage.checkCurrentUrlIsEqualToExpected;
import static utils.PageUtils.refreshPage;
import static utils.PageUtils.waitForPageUrlChangedTo;
import static utils.StringsGenerator.*;

public class MainPageTests extends WebTestBase {

    private LoginPage loginPage;
    private MainPage mainPage;

    @Parameters({"username", "password"})
    @BeforeMethod(description = "Login with valid User credentials")
    public void setUp(@Optional("john_dow@some.domaine.com") String username,
                      @Optional("123456789") String password) {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, password);
        mainPage = new MainPage(driver);
    }

    @Test(description = "Check that page header panel is displayed on the Main page",
            groups = {"ui", "main-page", "positive"})
    public void checkPageHeaderPanelIsDisplayed() {
        mainPage.checkHeaderPanelIsDisplayed();
    }

    @Test(description = "Check that Log Out button on the Main page has expected label",
            groups = {"ui", "main-page", "positive"})
    public void checkLogOutButtonHasExpectedLabel() {
        mainPage.checkLogOutButtonLabelEqualsTo("Выход");
    }

    @Test(description = "Check that Login page is opened after User clicked on the Log Out button on the Main page",
            groups = {"ui", "main-page", "positive"})
    public void checkLoginPageIsOpenedAfterUserClickedLogOutButton() {
        mainPage.checkLogOutButtonIsDisplayed();
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(LoginPage.URL, driver);
    }

    @Test(description = "Check that task input field is displayed on the Main page",
            groups = {"ui", "main-page", "positive"})
    public void checkTaskInputFieldIsDisplayed() {
        mainPage.checkTaskInputFieldIsDisplayed();
    }

    @Test(description = "Check that Add task button is displayed on the Main page",
            groups = {"ui", "main-page", "positive"})
    public void checkAddTaskButtonIsDisplayed() {
        mainPage.checkAddTaskButtonIsDisplayed();
    }

    @Test(description = "Check that Add task button has expected label",
            groups = {"ui", "main-page", "positive"})
    public void checkAddTaskButtonHasExpectedLabel() {
        mainPage.checkAddTaskButtonHasExpectedLabel("Добавить запись");
    }

    @Test(description = "Check that task input title is displayed",
            groups = {"ui", "main-page", "positive"})
    public void checkTaskInputTitleIsDisplayed() {
        mainPage.checkTaskInputTitleIsDisplayed();
    }

    @Test(description = "Check that task input has expected label",
            groups = {"ui", "main-page", "positive"})
    public void checkTaskInputHasExpectedLabel() {
        mainPage.checkTaskInputTitleEqualsTo("Управление");
    }

    @Test(description = "Check that tasks list title is displayed",
            groups = {"ui", "main-page", "positive"})
    public void checkTasksListTitleIsDisplayed() {
        mainPage.checkTasksListTitleIsDisplayed();
    }

    @Test(description = "Check that tasks list has expected label",
            groups = {"ui", "main-page", "positive"})
    public void checkTasksListHasExpectedLabel() {
        mainPage.checkTasksListTitleEqualsTo("Список дел");
    }

    @Test(description = "Check that User is able to add tasks to the tasks list",
            groups = {"ui", "main-page", "positive"})
    public void checkTasksCanBeAddedToTheTasksList() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Parameters({"username", "password"})
    @Test(description = "Check that the User's list of tasks is not get deleted after log out",
            groups = {"ui", "main-page", "positive"})
    public void checkListOfTasksRemainsAfterLogOut(String username, String password) {
        List<String> tasksToBeAdded = generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(description = "Check that User's list of tasks doesn't get wiped out on page refresh",
            groups = {"ui", "main-page", "positive"})
    public void checkListOfTasksRemainsAfterPageRefresh() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        refreshPage(driver);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(description = "Check that list of tasks is correctly numerated",
            groups = {"ui", "main-page", "positive"})
    public void checkTasksInListAreCorrectlyNumerated() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksNumerationIsCorrect(tasksToBeAdded);
    }

    @Test(description = "Check that user is not able to add empty task to the tasks list",
            groups = {"ui", "main-page", "negative"})
    public void checkUserNotAbleToAddEmptyTaskToTasksList() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(1, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.addTask(EMPTY);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(description = "Check that user is not able to add blank task to the tasks list",
            groups = {"ui", "main-page", "negative"})
    public void checkUserNotAbleToAddBlankTaskToTasksList() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(1, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.addTask(BLANK);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(description = "Check that User is not able to have more than 10 tasks simultaneously",
            groups = {"ui", "main-page", "positive"})
    public void checkUserCantHaveMoreThanTenTasksSimultaneously() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.sendKeysToTaskInputField(getRandomString(15));
        mainPage.clickOnAddTaskButton();
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(description = "Check that each task row has remove button",
            groups = {"ui", "main-page", "positive"})
    public void checkEachTaskRowHasRemoveButton() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkEachTaskHasRemoveButton();
    }

    @AfterMethod
    public void cleanUp() {
        if(mainPage != null) {
            mainPage.clearTasksList();
        }
        driver.manage().deleteAllCookies();
    }
}
