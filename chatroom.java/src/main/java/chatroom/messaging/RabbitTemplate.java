package chatroom.messaging;

import chatroom.core.Client;
import chatroom.core.PropertiesReader;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitTemplate {
    private ConnectionFactory factory;
    private Connection receiveConnection;

    public RabbitTemplate() {
        factory = new ConnectionFactory();
        factory.setHost(PropertiesReader.RabbitProperty.getHost());
        factory.setPort(PropertiesReader.RabbitProperty.getPort());
        factory.setVirtualHost(PropertiesReader.RabbitProperty.getVHost());
        factory.setUsername(PropertiesReader.RabbitProperty.getUser());
        factory.setPassword(PropertiesReader.RabbitProperty.getPassword());

        try {
            receiveConnection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public Connection getReceiveConnection() {
        return receiveConnection;
    }

    public void send(Client client, String exchange, String message) throws IOException {
        Connection sendConnection = null;
        Channel channel = null;
        try {
            sendConnection = factory.newConnection();
            channel = sendConnection.createChannel();

            HashMap<String, Object> map = new HashMap<>();
            map.put("username", client.getClientName());
            map.put("clientId", client.getClientId());

            AMQP.BasicProperties properties = new AMQP.BasicProperties
                    .Builder()
                    .userId(PropertiesReader.RabbitProperty.getUser())
                    .headers(map)
                    .build();

            channel.basicPublish(exchange, "", properties, message.getBytes());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                sendConnection.close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public String receive(String exchange, String queue, Client client) throws IOException {
        Channel channel = receiveConnection.createChannel();
        /**
         * - Name
         * - Durable (the queue will survive a broker restart)
         * - Exclusive (used by only one connection and the queue will be deleted when that connection closes)
         * - Auto-delete (queue that has had at least one consumer is deleted when last consumer unsubscribes)
         * - Args (optional; used by plugins and broker-specific features such as message TTL, queue length limit, etc)
         * */
        channel.queueDeclare(queue, false, true, true, null);
        channel.queueBind(queue, exchange, "");//exchange=room, queue=username

        String consumerTag = channel.basicConsume(queue, true, (s, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);

            AMQP.BasicProperties receivedProps = delivery.getProperties();
            Map<String, Object> objectMap = receivedProps.getHeaders();

            LongString username = (LongString) objectMap.get("username");
            String from = new String(username.getBytes(), StandardCharsets.UTF_8);

            LongString clientId = (LongString) objectMap.get("clientId");
            if (clientId == null || !clientId.equals(client.getClientId()))//თუ ეს მესიჯი ჩემი გაგზავნილი არაა
                System.out.println(from+": "+msg);
        }, (s) -> {});
        System.out.println("Thread Active Count = " + Thread.activeCount());
        client.setReceivingChannel(channel);
        return consumerTag;
    }

    public void unsubscribe(String consumerTag, Client client) throws IOException {
        client.getReceivingChannel().basicCancel(consumerTag);
    }

}
