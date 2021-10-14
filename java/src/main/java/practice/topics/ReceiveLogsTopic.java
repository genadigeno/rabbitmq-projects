package practice.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;
import practice.workqueues.NewTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsTopic {
    private static final Logger LOGGER = LogManager.getLogger(ReceiveLogsTopic.class);
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQProperty.getConnectionFactory().newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        if (args.length < 1){
            LOGGER.error("");
            System.exit(1);
        }

        for (String routingKey: args){
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }
        LOGGER.info("waiting for message, to exit press CTRL+F2 or CTRL+C");

        channel.basicConsume(queueName, false, (s, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("Received Message: {}", msg);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }, c->{});
    }
}
