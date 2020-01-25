package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import static utils.PropertyReader.getProperty;

public class WebElementsUtils {

    public static List<String> collectWebElementsLabels(List<WebElement> elements) {
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static void waitForWebElementInvisibility(WebElement element, WebDriver driver) {
        int timeoutSec = Integer.parseInt(getProperty("implicitly.wait.timeout"));
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, timeoutSec, 500);
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException ex) {
            throw new IllegalStateException(String.format("Element %s hasn't become visible after %d seconds", element, timeoutSec), ex);
        }
    }

    public static String getWebElementTextExcludingChildren(WebElement element) {
        String text = element.getText();
        for (WebElement child : element.findElements(By.xpath("./*"))) {
            text = text.replaceFirst(child.getText(), "");
        }
        return text.trim();
    }
}
