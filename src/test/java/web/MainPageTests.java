package web;

import base.web.WebTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import utils.StringsListGenerator;

import java.util.List;

import static pages.BasePage.checkCurrentUrlIsEqualToExpected;
import static utils.PageUtils.waitForPageUrlChangedTo;

public class MainPageTests extends WebTestBase {

    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeMethod
    public void login() {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("john_dow@some.domaine.com", "123456789");
        mainPage = new MainPage(driver);
    }

    @Test
    public void checkPageHeaderPanelIsDisplayed() {
        mainPage.checkHeaderPanelIsDisplayed();
    }

    @Test
    public void checkLogOutButtonHasExpectedLabel() {
        mainPage.checkLogOutButtonLabelEqualsTo("Выход");
    }

    @Test
    public void checkLoginPageIsOpenedAfterUserClickedLogOutButton() {
        mainPage.checkLogOutButtonIsDisplayed();
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(LoginPage.URL, driver);
    }

    @Test
    public void checkTaskInputFieldIsDisplayed() {
        mainPage.checkTaskInputFieldIsDisplayed();
    }

    @Test
    public void checkAddTaskButtonIsDisplayed() {
        mainPage.checkAddTaskButtonIsDisplayed();
    }

    @Test
    public void checkAddTaskButtonHasExpectedLabel() {
        mainPage.checkAddTaskButtonHasExpectedLabel("Добавить запись");
    }

    @Test
    public void checkTaskInputTitleIsDisplayed() {
        mainPage.checkTaskInputTitleIsDisplayed();
    }

    @Test
    public void checkTaskInputHasExpectedLabel() {
        mainPage.checkTaskInputTitleEqualsTo("Управление");
    }

    @Test
    public void checkTasksListTitleIsDisplayed() {
        mainPage.checkTasksListTitleIsDisplayed();
    }

    @Test
    public void checkTasksListHasExpectedLabel() {
        mainPage.checkTasksListTitleEqualsTo("Список дел");
    }

    @Test
    public void checkTasksCanBeAddedToTheTasksList() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test
    public void checkListOfTasksDescriptionsRemainsAfterLogOut() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        loginPage.login("john_dow@some.domaine.com", "123456789");
        waitForPageUrlChangedTo(MainPage.URL, driver);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test
    public void checkTasksInListAreCorrectlyNumerated() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksNumerationIsCorrect(tasksToBeAdded);
    }

    @Test
    public void checkUserNotAbleToAddEmptyTaskToTasksList() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(1, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.addTask("");
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    //Clear input doesn't work as expected
    @Test
    public void checkTaskIsNotAddedAfterUserClearedInput() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(1, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.sendKeysToTaskInputField(StringsListGenerator.getRandomString(15));
        mainPage.clearTaskInputField();
        mainPage.clickOnAddTaskButton();
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test
    public void checkUserCantAddMoreThanTenTasks() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.sendKeysToTaskInputField(StringsListGenerator.getRandomString(15));
        mainPage.clickOnAddTaskButton();
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test
    public void checkEachTaskRowHasRemoveButton() {
        List<String> tasksToBeAdded = StringsListGenerator.generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkEachTaskHasRemoveButton();
    }

    @AfterMethod
    public void cleanUp() {
        mainPage.clearTasksList();
        driver.manage().deleteAllCookies();
    }
}
