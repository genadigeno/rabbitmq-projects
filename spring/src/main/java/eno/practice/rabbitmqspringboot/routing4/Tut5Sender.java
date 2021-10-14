package eno.practice.rabbitmqspringboot.routing4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut5Sender {
    private static final Logger LOGGER = LogManager.getLogger(Tut6Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange topic;

    private AtomicInteger index = new AtomicInteger(0);
    private AtomicInteger count = new AtomicInteger(0);
    private final String[] keys = { "quick.orange.rabbit", "lazy.orange.elephant",
            "quick.orange.fox", "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox" };

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        if (index.incrementAndGet() == keys.length) {
            index.set(0);
        }
        String key = keys[index.get()];

        StringBuilder builder = new StringBuilder("Hello to ");
        builder.append(key).append(' ');
        builder.append(count.incrementAndGet());

        String message = builder.toString();

        rabbitTemplate.convertAndSend(topic.getName(), key, message);
        LOGGER.info("Message: {} sent", message);
    }
}
