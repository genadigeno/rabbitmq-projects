package practice.publishesubscrube;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import practice.configuration.RabbitMQProperty;
import practice.workqueues.NewTask;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class EmitLog {
    private static final Logger LOGGER = LogManager.getLogger(EmitLog.class);
    private static final String FANOUT_EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQProperty.getConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();){

            String message = args.length < 1 ? "Hello World" : String.join(" ", args);

            channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            channel.basicPublish(FANOUT_EXCHANGE_NAME,"",null, message.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("message sent");
        }
    }
}
