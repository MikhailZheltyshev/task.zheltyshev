package pages;

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

    public void open() {
        driver.get(URL);
    }

    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

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

    public void checkLoginButtonHasExpectedText(String expectedText) {
        assertThat(loginButton.getText())
                .as("Login button should have correct text")
                .isEqualTo(expectedText);
    }

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
