package utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUtils {

    public static String getCurrentPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public static void waitForPageUrlChangedTo(String targetUrl, WebDriver driver) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe(targetUrl));
        } catch (TimeoutException e) {
            throw new IllegalStateException(String.format("Page's URL is not changed to %s after %d seconds", targetUrl, 10), e);
        }
    }
}
