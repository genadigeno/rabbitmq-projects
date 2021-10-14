package eno.practice.rabbitmqspringboot.routing4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut4Sender {
    private static final Logger LOGGER = LogManager.getLogger(Tut6Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange direct;

    private AtomicInteger index = new AtomicInteger(0);
    private AtomicInteger count = new AtomicInteger(0);
    private final String[] keys = { "orange", "black", "green" };

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (index.incrementAndGet() == 3) {
            index.set(1);
        }

        String key = keys[index.get()];
        builder.append(key).append(' ');
        builder.append(count.get());

        String message = builder.toString();

        rabbitTemplate.convertAndSend(direct.getName(), key, message);
        LOGGER.info("Message: {} sent", message);
    }
}
