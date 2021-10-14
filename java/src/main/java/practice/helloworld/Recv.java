package practice.helloworld;

import com.rabbitmq.client.*;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQProperty.getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("waiting for message, to exit press CTRL+F2 or CTRL+C");

        DeliverCallback callback = (String consumerTag, Delivery delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Message: " + message);
        };
        channel.basicConsume(QUEUE_NAME, true, callback, ct ->{});
    }
}
