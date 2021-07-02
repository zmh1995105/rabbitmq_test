package com.rabbitmq.helloworld;


import com.rabbitmq.client.*;
import com.rabbitmq.utils.RabbitConstant;
import com.rabbitmq.utils.RabbitUtils;

import java.io.IOException;


public class Consumer {
    public static void main(String[] args) throws IOException {
        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false,
                false, false, null);

        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD, false, new Receiver(channel));

    }
}

class Receiver extends DefaultConsumer {
    private Channel channel;
    public Receiver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body);
        System.out.println("received: " + msg);

        System.out.println("msg tag id: " + envelope.getDeliveryTag());

        channel.basicAck(envelope.getDeliveryTag(), false);



    }
}

