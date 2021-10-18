package chatroom.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static Properties jdbcProperty;
    private static Properties rabbitmqProperty;
    private static final String PROPERTY_RABBITMQ_NAME = "rabbitmq.properties";
    private static final String PROPERTY_JDBC_NAME = "jdbc.properties";

    private PropertiesReader(){}

    static {
        try (InputStream in1 = PropertiesReader.class.getClassLoader().getResourceAsStream(PROPERTY_JDBC_NAME);
             InputStream in2 = PropertiesReader.class.getClassLoader().getResourceAsStream(PROPERTY_RABBITMQ_NAME)) {

            jdbcProperty = new Properties();
            jdbcProperty.load(in1);

            rabbitmqProperty = new Properties();
            rabbitmqProperty.load(in2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class RabbitProperty {
        public static String getHost() {
            return rabbitmqProperty.getProperty("rabbitmq.host");
        }

        public static int getPort() {
            return Integer.parseInt(rabbitmqProperty.getProperty("rabbitmq.port"));
        }

        public static String getVHost() {
            return rabbitmqProperty.getProperty("rabbitmq.vhost");
        }

        public static String getUser() {
            return rabbitmqProperty.getProperty("rabbitmq.user");
        }

        public static String getPassword() {
            return rabbitmqProperty.getProperty("rabbitmq.password");
        }
    }

    static String getProperty(String propertyName) {
        return jdbcProperty.getProperty(propertyName);
    }
}
