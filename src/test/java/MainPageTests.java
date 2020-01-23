import base.WebTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import utils.TaskListGenerator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.PageUtils.getCurrentPageUrl;
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
    public void checkLogOutButtonHasExpectedLabel() {
        mainPage.checkLogOutButtonLabelEqualsTo("Выход");
    }

    @Test
    public void checkLoginPageIsOpenedAfterUserClickedLogOutButton() {
        mainPage.checkLogOutButtonIsDisplayed();
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        assertThat(getCurrentPageUrl(driver))
                .as("User should be logged out and redirected to the Login page")
                .isEqualTo(LoginPage.URL);
    }

    @Test
    public void checkTasksCanBeAddedToTheTasksList() {
        List<String> tasksToBeAdded = TaskListGenerator.generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @Test
    public void checkListOfTasksDescriptionsRemainsAfterLogOut() {
        List<String> tasksToBeAdded = TaskListGenerator.generateListOfRandomStrings(5, 15);
        mainPage.checkTaskInputFieldIsDisplayed();
        mainPage.addTasks(tasksToBeAdded);
        mainPage.clickOnLogOutButton();
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        loginPage.login("john_dow@some.domaine.com", "123456789");
        waitForPageUrlChangedTo(MainPage.URL, driver);
        mainPage.checkTasksDescriptionsEqualToExpected(tasksToBeAdded);
    }

    @AfterMethod
    public void cleanUp() {
        mainPage.clearTasksList();
        driver.manage().deleteAllCookies();
    }
}
