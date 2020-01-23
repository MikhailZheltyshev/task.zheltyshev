package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    public static final String URL = baseUrl + "/main";

    @FindBy(tagName = "header")
    private WebElement header;

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[text()='Список дел']")
    private WebElement toDoListTitle;

    @FindBy(xpath = "//div[text()='Управление']")
    private WebElement taskInputTitle;

    @FindBy(css = ".create-form input")
    private WebElement taskInputField;

    @FindBy(css = ".create-form button")
    private WebElement addTaskButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }
}
