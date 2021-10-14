package practice.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Delivery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsDirect {
    private static final Logger LOGGER = LogManager.getLogger(ReceiveLogsDirect.class);
    private static final String DIRECT_EXCHANGE_NAME = "tut.direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQProperty.getConnectionFactory().newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        if (args.length < 1) {
            LOGGER.info("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        /* ერთი კონკრეტული queue-სთვის რამოდენიმე routingKey-ის მიბმა */
        for (String routingKey : args)
            channel.queueBind(queueName, DIRECT_EXCHANGE_NAME, routingKey);

        LOGGER.info("waiting for message, to exit press CTRL+F2 or CTRL+C");

        channel.basicConsume(queueName, false, (String deliveryTag, Delivery delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("Received Message: {}", message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }, t -> {});
    }
}
