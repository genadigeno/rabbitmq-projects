package eno.practice.rabbitmqspringboot.routing;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Tut6Client {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    private int start;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println("");
        Integer response = (Integer) template.convertSendAndReceive(
                exchange.getName(),
                "rpc", start++);
        System.out.println("sent: "+start+",Got: "+response);
    }
}
