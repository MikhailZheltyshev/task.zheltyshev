import base.WebTestBase;
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

    @Test
    public void checkLoginFormElementsAreDisplayed() {
        loginPage.open();
        loginPage.checkLoginFormElementsAreDisplayed();
    }

    @Test
    public void checkLoginButtonHasExpectedText() {
        loginPage.open();
        loginPage.checkLoginButtonHasExpectedText("Войти");
    }

    @Test(dataProvider = "valid-creds-provider", dataProviderClass = DataProviders.class)
    public void checkLoginWithValidCredentials(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        checkCurrentUrlIsEqualToExpected(MainPage.URL, driver);
    }

    @Test(dataProvider = "invalid-creds-provider", dataProviderClass = DataProviders.class)
    public void checkLoginWithInValidCredentials(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        loginPage.checkLoginErrorMessageIsDisplayedWithExpectedText("Неверные логин или пароль");
    }

    @AfterMethod
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }
}
