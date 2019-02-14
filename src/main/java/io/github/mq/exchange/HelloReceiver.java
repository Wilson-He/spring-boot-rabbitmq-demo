package io.github.mq.exchange;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * HelloReceiverer
 *
 * @author Wilson
 * @date 2019/2/13
 */
@Component
public class HelloReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }

    //    @RabbitListener(queues = "helloQueue",containerFactory = "simpleContainer")
    @RabbitListener(queues = "helloQueue")
    public void process(String hello) {
        System.out.println("HelloReceiver thread:" + Thread.currentThread().getName());
        System.out.println("Receiver1  : " + hello);
    }

    @RabbitListener(queues = "helloQueue")
    public void process2(String hello) {
        System.out.println("HelloReceiver thread:" + Thread.currentThread().getName());
        System.out.println("Receiver2  : " + hello);
    }

    @RabbitListener(queues = "fanout.a")
    public void fanoutA(String hello) {
        System.out.println("fanout.a:" + hello);
    }

    @RabbitListener(queues = "fanout.b")
    public void fanoutB(String hello) {
        System.out.println("fanout.b:" + hello);
    }

    @RabbitListener(queues = "fanout.c")
    public void fanoutC(String hello) {
        System.out.println("fanout.c:" + hello);
    }
}
