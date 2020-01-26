package utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.PropertyReader.getPageLoadTimeOutInSeconds;

public class PageUtils {

    public static String getCurrentPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public static void navigateBackToPreviousPage(WebDriver driver) {
        driver.navigate().back();
    }

    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static void waitForPageUrlChangedTo(String targetUrl, WebDriver driver) {
        int timeOut = getPageLoadTimeOutInSeconds();
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.urlToBe(targetUrl));
        } catch (TimeoutException e) {
            throw new IllegalStateException(String.format("Page's URL is not changed to %s after %d seconds", targetUrl, timeOut), e);
        }
    }
}
