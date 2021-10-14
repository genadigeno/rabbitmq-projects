package practice.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {
    private static final Logger LOGGER = LogManager.getLogger(EmitLogDirect.class);
    private static final String DIRECT_EXCHANGE_NAME = "tut.direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        try (Connection connection = RabbitMQProperty.getConnectionFactory().newConnection();
             Channel channel = connection.createChannel();) {

            channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String message = "Test";
            String routingKey = getSeverity(args);

            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, message.getBytes());
            LOGGER.info("Message: {} sent", message);
        }
    }

    private static String getSeverity(String[] strings) {
        if (strings.length < 1)
            return "info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length <= startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
