package practice.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = RabbitMQProperty.getConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();){

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hi";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("message sent");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
