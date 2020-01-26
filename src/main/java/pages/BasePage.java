package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.PageUtils.getCurrentPageUrl;
import static utils.PropertyReader.getAppBaseUrl;
import static utils.PropertyReader.getAppPort;

public class BasePage {

    protected WebDriver driver;
    protected static final String baseUrl = String.format("%s:%s", getAppBaseUrl(), getAppPort());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        initPage(driver);
    }

    protected void open(String url) {
        driver.get(url);
    }

    private void initPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @Step("Check that page URL equals to expected {0}")
    public static void checkCurrentUrlIsEqualToExpected(String expectedUrl, WebDriver driver) {
        assertThat(getCurrentPageUrl(driver))
                .as("Actual page URL should be equal to expected")
                .isEqualTo(expectedUrl);
    }
}
