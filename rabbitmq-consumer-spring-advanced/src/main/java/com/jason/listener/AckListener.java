package com.jason.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class AckListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("message: " + new String(message.getBody()));
            System.out.println("running =====");
            System.out.println("receive msg: " + deliveryTag);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

            e.printStackTrace();
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
