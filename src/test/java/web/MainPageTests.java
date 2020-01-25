package web;

import base.web.WebTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;

import java.util.List;

import static pages.BasePage.checkCurrentUrlIsEqualToExpected;
import static utils.PageUtils.waitForPageUrlChangedTo;
import static utils.StringsGenerator.generateListOfRandomStrings;
import static utils.StringsGenerator.getRandomString;

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

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkPageHeaderPanelIsDisplayed() {
        mainPage.checkHeaderPanelIsDisplayed();
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkLogOutButtonHasExpectedLabel() {
        mainPage.checkLogOutButtonLabelEqualsTo("Выход");
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkLoginPageIsOpenedAfterUserClickedLogOutButton() {
        mainPage.checkLogOutButtonIsDisplayed();
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(LoginPage.URL, driver);
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTaskInputFieldIsDisplayed() {
        mainPage.checkTaskInputFieldIsDisplayed();
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkAddTaskButtonIsDisplayed() {
        mainPage.checkAddTaskButtonIsDisplayed();
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkAddTaskButtonHasExpectedLabel() {
        mainPage.checkAddTaskButtonHasExpectedLabel("Добавить запись");
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTaskInputTitleIsDisplayed() {
        mainPage.checkTaskInputTitleIsDisplayed();
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTaskInputHasExpectedLabel() {
        mainPage.checkTaskInputTitleEqualsTo("Управление");
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTasksListTitleIsDisplayed() {
        mainPage.checkTasksListTitleIsDisplayed();
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTasksListHasExpectedLabel() {
        mainPage.checkTasksListTitleEqualsTo("Список дел");
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTasksCanBeAddedToTheTasksList() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkListOfTasksDescriptionsRemainsAfterLogOut() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        loginPage.login("john_dow@some.domaine.com", "123456789");
        waitForPageUrlChangedTo(MainPage.URL, driver);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTasksInListAreCorrectlyNumerated() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksNumerationIsCorrect(tasksToBeAdded);
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkUserNotAbleToAddEmptyTaskToTasksList() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(1, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.addTask("");
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    //Clear input doesn't work as expected
    @Test(groups = {"ui", "main-page", "positive"})
    public void checkTaskIsNotAddedAfterUserClearedInput() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(1, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.sendKeysToTaskInputField(getRandomString(15));
        mainPage.clearTaskInputField();
        mainPage.clickOnAddTaskButton();
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkUserCantAddMoreThanTenTasks() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(10, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.sendKeysToTaskInputField(getRandomString(15));
        mainPage.clickOnAddTaskButton();
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test(groups = {"ui", "main-page", "positive"})
    public void checkEachTaskRowHasRemoveButton() {
        List<String> tasksToBeAdded = generateListOfRandomStrings(10, 15);
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
