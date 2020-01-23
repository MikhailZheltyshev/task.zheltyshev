import base.WebTestBase;
import dataProviders.DataProviders;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import utils.PageUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.PageUtils.*;
import static utils.PageUtils.getCurrentPageUrl;

public class LoginPageTests extends WebTestBase {

    private LoginPage loginPage;

    @Test
    public void checkLoginFormElementsAreDisplayed() {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.checkLoginFormElementsAreDisplayed();
    }

    @Test
    public void checkLoginButtonHasExpectedText() {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.checkLoginButtonHasExpectedText("Войти");
    }

    @Test(dataProvider = "valid-creds-provider", dataProviderClass = DataProviders.class)
    public void checkLoginWithValidCredentials(String username, String password) {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, password);
        waitForPageUrlChangedTo(MainPage.URL, driver);
        assertThat(getCurrentPageUrl(driver))
                .as("User should be navigated to the Main page")
                .isEqualTo(MainPage.URL);
    }

    @Test(dataProvider = "invalid-creds-provider", dataProviderClass = DataProviders.class)
    public void checkLoginWithInValidCredentials(String username, String password) {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, password);
        loginPage.checkLoginErrorMessageIsDisplayedWithExpectedText("Неверные логин или пароль");
    }
}
