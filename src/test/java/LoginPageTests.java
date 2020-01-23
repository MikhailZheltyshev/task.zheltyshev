import base.WebTestBase;
import dataProviders.DataProviders;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import utils.PageUtils;

import static org.assertj.core.api.Assertions.*;
import static utils.PageUtils.*;

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
        loginPage.waitForPageUrlChangedTo(MainPage.URL);
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
