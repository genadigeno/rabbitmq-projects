package practice.publishesubscrube;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs {
    private static final Logger LOGGER = LogManager.getLogger(ReceiveLogs.class);
    private static final String FANOUT_EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQProperty.getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, FANOUT_EXCHANGE_NAME, "");//s2 is routeKey

        LOGGER.info("waiting for message, to exit press CTRL+F2 or CTRL+C");

        channel.basicConsume(queueName, false, (String deliveryTag, Delivery delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("Received Message: {}", message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }, c -> {});
    }
}
