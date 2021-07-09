package com.jason.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer2.xml")
public class JasonTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConfirm() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // if msg arrive exchange, confirm will be sent back.
                // whatever if msg will arrive queue or not
                if (ack) {
                    System.out.println("successfully receive!");
                } else {
                    System.out.println("failed to receive:" + cause);
                }
            }
        });

        for (int i=0; i < 5; i++) {
            rabbitTemplate.convertAndSend("test_exchange_confirm",
                    "confirm123", "msg confirm..." + i);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testReturn() {
        // set exchange mode when messages fail to send
        // if ture, msg will be sent back when fail to arrive queue
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("executing...");
                System.out.println("msg:" + message);
                System.out.println("replycode:" + replyCode);
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQos() {
        for (int i=0; i < 10; i ++) {
            rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm",
                    "message confirm ...");
        }
    }

    @Test
    public void testTtl() {
        for (int i=0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.123",
                    "test ttl..." + i);
        }
    }

    @Test
    public void testDlx() {
        rabbitTemplate.convertAndSend("test_exchange_dlx",
                "msg.dlx.hello", "test dlx...");

        for (int i=0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test_exchange_dlx",
                    "msg.dlx.hello", "test dlx..." + i);
        }

        rabbitTemplate.convertAndSend();


    }


}
