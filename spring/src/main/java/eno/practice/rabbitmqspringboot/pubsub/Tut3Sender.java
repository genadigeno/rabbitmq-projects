package eno.practice.rabbitmqspringboot.pubsub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut3Sender {
    private static final Logger LOGGER = LogManager.getLogger(Tut6Sender.class);
    private static final String ROUTING_KEY = "";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanout;

    private AtomicInteger dots  = new AtomicInteger(0);
    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder();
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }
        for (int i=0; i < dots.get(); i++) {
            builder.append('.');
        }
        builder.append(count.incrementAndGet());
        String message = builder.toString();

        rabbitTemplate.convertAndSend(fanout.getName(), ROUTING_KEY, message);
        LOGGER.info("Message: {} sent", message);
    }
}
