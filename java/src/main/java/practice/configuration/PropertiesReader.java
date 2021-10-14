package practice.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertiesReader {
    private Properties properties;
    private static PropertiesReader instance = null;
    private static final String PROPERTY_NAME = "rabbitmq.properties";

    private PropertiesReader(String propertyFleName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFleName);
        properties = new Properties();
        properties.load(is);
    }

    static PropertiesReader getInstance() {
        if (instance == null) {
            synchronized (PropertiesReader.class) {
                if (instance == null) {
                    try {
                        instance = new PropertiesReader(PROPERTY_NAME);
                        return instance;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

}
