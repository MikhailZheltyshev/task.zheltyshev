package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertyReader {

    private static final String DEFAULT_PROPS_FILE = "test.properties";
    private static final String PROPS_FILE_ENV_VAR = "propsFile";

    public static String getAppBaseUrl() {
        return getProperty("app.url");
    }

    public static String getAppPort() {
        return getProperty("app.port");
    }

    public static String getBrowserName() {
        return getProperty("browser");
    }

    public static int getImplicitlyWaitTimeOutInSeconds() {
        return Integer.parseInt(getProperty("implicitly.wait.timeout"));
    }

    public static int getPageLoadTimeOutInSeconds() {
        return Integer.parseInt(getProperty("page.load.timeout"));
    }

    private static String getProperty(String propertyKey) {
        final String propertyValue = loadProperties().getProperty(propertyKey);
        if (propertyValue == null) {
            throw new IllegalStateException(String.format("Property with [%s] key not found", propertyKey));
        }
        return propertyValue;
    }

    private static Properties loadProperties() {
        final Properties props = new Properties();
        final String propertiesFileName = getPropertiesFileName();
        try (InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream(propertiesFileName)) {
            props.load(resourceAsStream);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to load properties %s from resources", propertiesFileName),e);
        }
        return props;
    }

    private static String getPropertiesFileName() {
        final Map<String, String> env = System.getenv();
        return env.get(PROPS_FILE_ENV_VAR) == null ? DEFAULT_PROPS_FILE : DEFAULT_PROPS_FILE;
    }
}
