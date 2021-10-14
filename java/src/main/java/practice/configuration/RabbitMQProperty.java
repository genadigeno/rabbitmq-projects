package practice.configuration;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQProperty {
    private static final PropertiesReader reader = PropertiesReader.getInstance();

    private static final String HOST_PROPERTY = "rabbitmq.host";
    private static final String PORT_PROPERTY = "rabbitmq.port";
    private static final String VHOST_PROPERTY = "rabbitmq.vhost";
    private static final String PASSWORD_PROPERTY = "rabbitmq.password";
    private static final String USER_PROPERTY = "rabbitmq.user";
    private static final String URI_PROPERTY = "rabbitmq.uri";

    private RabbitMQProperty() {}

    public static String getHost() {
        return reader.getProperty(HOST_PROPERTY);
    }

    public static String getPort() {
        return reader.getProperty(PORT_PROPERTY);
    }

    public static int getPortInteger() throws NumberFormatException {
        return Integer.parseInt(getPort());
    }

    public static String getVHost() {
        return reader.getProperty(VHOST_PROPERTY);
    }

    public static String getUser() {
        return reader.getProperty(USER_PROPERTY);
    }

    public static String getPassword() {
        return reader.getProperty(PASSWORD_PROPERTY);
    }

    public static String getUri() {
        StringBuilder sb = new StringBuilder();
        sb.append("amqp://");
        sb.append(getUser());
        sb.append(":");
        sb.append(getPassword());
        sb.append("@");
        sb.append(getHost());
        sb.append(":");
        sb.append(getPort());
        sb.append(getVHost());
        return sb.toString();
    }

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(getHost());
        cf.setPort(getPortInteger());
        cf.setVirtualHost(getVHost());
        cf.setUsername(getUser());
        cf.setPassword(getPassword());
        return cf;
    }
}
