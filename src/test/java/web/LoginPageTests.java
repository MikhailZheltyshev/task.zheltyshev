package web;

import base.web.WebTestBase;
import dataProviders.DataProviders;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;

import static pages.BasePage.checkCurrentUrlIsEqualToExpected;
import static utils.PageUtils.waitForPageUrlChangedTo;

public class LoginPageTests extends WebTestBase {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
    }

    @Test(groups = {"ui", "login-page", "positive"})
    public void checkLoginFormElementsAreDisplayed() {
        loginPage.open();
        loginPage.checkLoginFormElementsAreDisplayed();
    }

    @Test(groups = {"ui", "login-page", "positive"})
    public void checkLoginButtonHasExpectedText() {
        loginPage.open();
        loginPage.checkLoginButtonHasExpectedText("Войти");
    }

    @Test(groups = {"ui", "login-page", "positive"},
            dataProvider = "valid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkLoginWithValidCredentials(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(MainPage.URL, driver);
    }

    @Test(groups = {"ui", "login-page", "negative"},
            dataProvider = "invalid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkLoginWithInValidCredentials(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        checkCurrentUrlIsEqualToExpected(LoginPage.URL, driver);
        loginPage.checkLoginErrorMessageIsDisplayedWithExpectedText("Неверные логин или пароль");
    }

    @AfterMethod
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }
}
