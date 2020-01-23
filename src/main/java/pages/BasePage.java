package pages;

import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

public class BasePage {

    protected WebDriver driver;
    protected final String baseUrl = PropertyReader.getProperty("url");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
