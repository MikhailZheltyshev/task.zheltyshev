package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.PageUtils.getCurrentPageUrl;
import static utils.PropertyReader.getProperty;

public class BasePage {

    protected WebDriver driver;
    protected static final String baseUrl = String.format("%s:%s", getProperty("app.url"), getProperty("app.port"));

    public BasePage(WebDriver driver) {
        this.driver = driver;
        initPage(driver);
    }

    private void initPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public static void checkCurrentUrlIsEqualToExpected(String expectedUrl, WebDriver driver) {
        assertThat(getCurrentPageUrl(driver))
                .as("Actual page URL should be equal to expected")
                .isEqualTo(expectedUrl);
    }
}
