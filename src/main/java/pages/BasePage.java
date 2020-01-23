package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.PageUtils;
import utils.PropertyReader;

public class BasePage {

    protected WebDriver driver;
    protected static final String baseUrl = PropertyReader.getProperty("url");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        initPage(driver);
    }

    public void waitForPageUrlChangedTo(String targetUrl) {
        PageUtils.waitForPageUrlChangedTo(targetUrl, driver);
    }

    private void initPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
