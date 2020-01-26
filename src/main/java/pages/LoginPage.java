package pages;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage extends BasePage {

    public static final String URL = baseUrl + "/login";

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = ".login-container button")
    private WebElement loginButton;

    @FindBy(className = "error")
    private WebElement loginErrorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Login page")
    public void open() {
        open(URL);
    }

    @Step("Perform login with \"{0}\" username and \"{1}\" password")
    public void login(String username, String password) {
        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    @Step("Check login form is displayed")
    public void checkLoginFormElementsAreDisplayed() {
        final SoftAssertions soft = new SoftAssertions();
        soft.assertThat(usernameField.isDisplayed())
                .as("Username field should be displayed on the Login page")
                .isTrue();
        soft.assertThat(passwordField.isDisplayed())
                .as("Password field should be displayed on the Login page")
                .isTrue();
        soft.assertThat(loginButton.isDisplayed())
                .as("Login button should be displayed on the Login page")
                .isTrue();
        soft.assertAll();
    }

    @Step("Check Login button has \"{0}\" as label")
    public void checkLoginButtonHasExpectedText(String expectedText) {
        assertThat(loginButton.getText())
                .as("Login button should have correct text")
                .isEqualTo(expectedText);
    }

    @Step("Check Login error message is displayed with \"{0}\" text")
    public void checkLoginErrorMessageIsDisplayedWithExpectedText(String expectedErrorText) {
        final SoftAssertions soft = new SoftAssertions();
        soft.assertThat(loginErrorMessage.isDisplayed())
                .as("Login error message should be displayed")
                .isTrue();
        soft.assertThat(loginErrorMessage.getText())
                .as("Login error message text should be: " + expectedErrorText)
                .isEqualTo(expectedErrorText);
        soft.assertAll();
    }
}
