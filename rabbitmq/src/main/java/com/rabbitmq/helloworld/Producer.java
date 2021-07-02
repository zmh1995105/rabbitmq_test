package com.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.RabbitConstant;
import com.rabbitmq.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main (String[] args) throws IOException, TimeoutException {
        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();
        System.out.println("===connected===");
        String msg = "jason test";
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false, false, null);
        System.out.println("===declared");
        channel.basicPublish("", RabbitConstant.QUEUE_HELLOWORLD,
                null, msg.getBytes());
        System.out.println("==sent===");
        channel.close();
        conn.close();
        System.out.println("===closed===");
    }
}
