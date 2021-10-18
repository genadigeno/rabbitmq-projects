package chatroom;

import chatroom.messaging.RabbitTemplate;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RabbitTemplateTest {
    private RabbitTemplate rabbitTemplate = new RabbitTemplate();
    private String room = "room.1";
    private String testMessage = "Hi";

    @Test
    public void connection_test() throws IOException {
        Channel channel = rabbitTemplate.getReceiveConnection().createChannel();
        channel.queueDeclare("clientId", false, false, false, null);
        channel.queueBind("clientId", room, "");
    }

    @Test
    public void send_test() throws IOException {
        Channel channel = rabbitTemplate.getReceiveConnection().createChannel();
        channel.queueDeclare("clientId", false, false, false, null);
        channel.queueBind("clientId", room, "");


    }

    @Test
    public void receive_test() throws IOException {
        Channel channel = rabbitTemplate.getReceiveConnection().createChannel();
        channel.queueDeclare("clientId", false, false, false, null);
        channel.queueBind("clientId", room, "");

        channel.basicConsume("clientId", true, (s, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            assertEquals(testMessage, msg);
        }, (s) -> {});
    }

    @Test
    public void send() throws IOException {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("username", "TEST");

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(headers).build();

        Channel channel = rabbitTemplate.getReceiveConnection().createChannel();
        channel.basicPublish("room.1", "", props, testMessage.getBytes());
    }
}
