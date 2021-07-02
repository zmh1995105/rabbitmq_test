package com.rabbitmq.workqueue2;

import com.baiqi.rabbitmq.workqueue.SMS;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.RabbitConstant;
import com.rabbitmq.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @author JAson
 */

public class OrderSystem {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false,
                false, false, null);

        for (int i = 1; i < 100; i ++) {
            SMS sms = new SMS("p" + i, "13330000" + i, "you ticket: " + i);
            String jasonSMS = new Gson().toJson(sms);
            channel.basicPublish("", RabbitConstant.QUEUE_SMS, null, jasonSMS.getBytes());
        }

        System.out.println("sent");
        channel.close();
        connection.close();
    }

}
