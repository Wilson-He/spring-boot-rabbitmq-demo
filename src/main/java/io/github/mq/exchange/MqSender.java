package io.github.mq.exchange;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * HelloSender
 *
 * @author Wilson
 * @date 2019/2/13
 */
@Component
public class MqSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendHelloQueue(String msg) {
        this.rabbitTemplate.convertAndSend("helloQueue", msg);
    }

    public void sendFanout(String msg) {
        this.rabbitTemplate.convertAndSend("fanoutExchange","", msg);
    }
}
