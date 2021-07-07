package com.jason;

import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer2.xml")
public class Test {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @org.junit.Test
    public void testHelloWorld() {
        rabbitTemplate.convertAndSend("spring_queue", "hello world");
    }

    @org.junit.Test
    public void testFanout() {
        /**
         * 广播模式
         */
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "spring fanout ...");
    }

    @org.junit.Test
    public void testDirect() {
        /**
         * routing
         */
        rabbitTemplate.convertAndSend("spring_direct_exchange", "info", "");
    }

    @org.junit.Test
    public void testTopics() {
        /**
         * test pattern
         */
        rabbitTemplate.convertAndSend("spring_topic_exchange", "jason.haha", "spring topic ...");


    }
}

