import base.WebTestBase;
import dataProviders.DataProviders;
import org.testng.annotations.Test;
import pages.LoginPage;

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
    }

    @Test(dataProvider = "invalid-creds-provider", dataProviderClass = DataProviders.class)
    public void checkLoginWithInValidCredentials(String username, String password) {
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, password);
        loginPage.checkLoginErrorMessageIsDisplayedWithExpectedText("Неверные логин или пароль");
    }
}
