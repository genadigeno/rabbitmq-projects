package eno.practice.rabbitmqspringboot.pubsub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Tut3Receiver {
    private static final Logger LOGGER = LogManager.getLogger(Tut6Receiver.class);

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    /*@RabbitHandler(isDefault = true)*/
    private void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();

        LOGGER.info("instance {} [x] received '{}'", receiver, in);
        doWork(in);
        watch.stop();

        LOGGER.info("instance {} [x] Done in '{}'s", receiver, watch.getTotalTimeSeconds());
    }

    private void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray())
            if (ch == '.')
                Thread.sleep(1000);
    }
}
