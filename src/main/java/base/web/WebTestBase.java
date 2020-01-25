package base.web;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.PropertyReader;

import java.util.concurrent.TimeUnit;

import static utils.PropertyReader.getProperty;

public class WebTestBase {

    protected WebDriver driver;

    @BeforeClass
    public void initDriver() {
        String browserName = PropertyReader.getProperty("browser");
        switch (browserName.toUpperCase()) {
            case "CHROME":
                ChromeDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                FirefoxDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "IE":
                InternetExplorerDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "EDGE":
                EdgeDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalStateException("Desired browser is not supported: " + browserName);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("implicitly.wait.timeout")), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(getProperty("page.load.timeout")), TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
