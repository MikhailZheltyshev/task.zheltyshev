package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertyReader {

    private static final String DEFAULT_PROPS_FILE = "test.properties";
    private static final String PROPS_FILE_ENV_VAR = "testProperties";

    public static String getProperty(String propertyKey) {
        final String propertyValue = loadProperties().getProperty(propertyKey);
        if (propertyKey == null) {
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
            e.printStackTrace();
        }
        return props;
    }

    private static String getPropertiesFileName() {
        final Map<String, String> env = System.getenv();
        return env.get(PROPS_FILE_ENV_VAR) == null ? DEFAULT_PROPS_FILE : DEFAULT_PROPS_FILE;
    }
}
