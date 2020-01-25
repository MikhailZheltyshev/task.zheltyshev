package web;

import base.web.WebTestBase;
import dataProviders.DataProviders;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.MainPage;

import static pages.BasePage.checkCurrentUrlIsEqualToExpected;
import static utils.PageUtils.navigateBackToPreviousPage;
import static utils.PageUtils.waitForPageUrlChangedTo;

public class LoginPageTests extends WebTestBase {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Check that username field, password field and login button are displayed on the Login page",
            groups = {"ui", "login-page", "positive"})
    public void checkLoginFormElementsAreDisplayed() {
        loginPage.open();
        loginPage.checkLoginFormElementsAreDisplayed();
    }

    @Test(description = "Check that Log In button has expected text",
            groups = {"ui", "login-page", "positive"})
    public void checkLoginButtonHasExpectedText() {
        loginPage.open();
        loginPage.checkLoginButtonHasExpectedText("Войти");
    }

    @Test(description = "Check that User can log in with valid credentials",
            groups = {"ui", "login-page", "positive"},
            dataProvider = "valid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkLoginWithValidCredentials(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(MainPage.URL, driver);
    }

    @Test(description = "Check that User can't log in with invalid credentials",
            groups = {"ui", "login-page", "negative"},
            dataProvider = "invalid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkLoginWithInValidCredentials(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        checkCurrentUrlIsEqualToExpected(LoginPage.URL, driver);
        loginPage.checkLoginErrorMessageIsDisplayedWithExpectedText("Неверные логин или пароль");
    }

    @Parameters({"defaultUsername", "defaultPassword"})
    @Test(description = "Check that User can log in again after returning to Login page by browser's back button",
            groups = {"ui", "login-page", "negative"})
    public void checkUserCanLoginAgainAfterReturningBackToLoginPageFromMainPage(@Optional("john_dow@some.domaine.com") String username,
                                                                                @Optional("123456789") String password) {
        loginPage.open();
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        navigateBackToPreviousPage(driver);
        waitForPageUrlChangedTo(LoginPage.URL, driver);
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(MainPage.URL, driver);
    }

    @AfterMethod
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }
}
