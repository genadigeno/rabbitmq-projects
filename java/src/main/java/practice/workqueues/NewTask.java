package practice.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {
    private static final Logger LOGGER = LogManager.getLogger(NewTask.class);
    //private static final String QUEUE_NAME = "workqueues";
    private static final String QUEUE_NAME = "task_queue";
    private static boolean durable = true;

    public static void main(String[] args) {
        ConnectionFactory factory = RabbitMQProperty.getConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();){

            String message = String.join(" ", args);

            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            LOGGER.info("message sent");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
