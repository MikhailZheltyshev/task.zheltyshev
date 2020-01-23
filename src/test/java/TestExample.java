import base.WebTestBase;
import org.testng.annotations.Test;

public class TestExample extends WebTestBase {

    @Test
    public void testTry() {
        driver.get("http://www.google.com");
    }
}
