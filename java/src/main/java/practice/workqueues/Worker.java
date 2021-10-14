package practice.workqueues;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/** By default rabbitmq send each message to next consumer, in sequence.
 *  On average every consumer will get the same number of messages.
 *  Tis way of distributing messages is called "round-robin".
 * */
public class Worker {
    private static final Logger LOGGER = LogManager.getLogger(Worker.class);
    //private static final String QUEUE_NAME = "workqueues";
    private static final String QUEUE_NAME = "task_queue";
    private static boolean autoAck = false;
    private static boolean durable = true;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQProperty.getConnectionFactory().newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        channel.basicQos(1);//prefetchCount=1 - meaning not to give more than one message to a worker
        LOGGER.info("waiting for message, to exit press CTRL+F2 or CTRL+C");

        DeliverCallback callback = (String consumerTag, Delivery delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("Message: {}", message);

            try {
                doWork(message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        channel.basicConsume(QUEUE_NAME, autoAck, callback, ct ->{});
        LOGGER.info("[x] Done");
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray())
            if (ch == '.')
                Thread.sleep(1000);
    }
}
