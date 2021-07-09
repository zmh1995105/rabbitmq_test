package com.jason.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class QosListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(new String(message.getBody()));
        Thread.sleep(1000);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
