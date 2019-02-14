package io.github.mq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * SimpleListener
 *
 * @author Wilson
 * @date 2019/2/13
 */
@Component
public class SimpleListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("delivery tag:" + message.getMessageProperties().getDeliveryTag());
        System.out.println("consumer tag:" + message.getMessageProperties().getConsumerTag());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
