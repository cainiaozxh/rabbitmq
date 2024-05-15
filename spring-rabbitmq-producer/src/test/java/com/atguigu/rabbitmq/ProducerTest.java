package com.atguigu.rabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq
 * @CLASSNAME: ProducerTest
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/13 16:44
 * @SINCE 17.0.7
 * @DESCRIPTION: ProducerTest
 */
@SpringJUnitConfig(locations = {"classpath:spring-rabbitmq.xml"})
public class ProducerTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void simpleQueue() {
        String message = "Hello,spring_rabbitmq, 你好 simple_queue";
        String queueName = "simple_queue";
        rabbitTemplate.convertAndSend("",queueName,message);
    }

    @Test
    public void fanoutQueue() {
        String baseMsg = "Hello spring_fanout,你好 发布订阅模式 ";
        String exchangeName = "spring_fanout_exchange";
        String routingKey1 = "aaa";
        for (int i = 0; i < 10; i++) {
            String message = baseMsg + "- " + i;
            rabbitTemplate.convertAndSend(exchangeName, routingKey1, message);
        }
    }

    @Test
    public void topicQueue() {
        //6 发送消息
        String message1 = "hello topic, bindingKey = *.orange.* -message1";
        String message2 = "hello topic, bindingKey = #.error -message2";
        String message3 = "hello topic, bindingKey = order.* -message3";
        String message4 = "hello topic, bindingKey = *.*.rabbit -message4";
        String message5 = "hello topic, bindingKey = lazy.# -message5";
        String message6 = "hello topic, bindingKey = lazy.# -message6";
        String exchangeName = "spring_topic_exchange";

        rabbitTemplate.convertAndSend(exchangeName, "a.orange.rabbit", message1);
        rabbitTemplate.convertAndSend(exchangeName, "order.error", message2);
        rabbitTemplate.convertAndSend(exchangeName, "order.error", message3);
        rabbitTemplate.convertAndSend(exchangeName, "a.b.rabbit", message4);
        rabbitTemplate.convertAndSend(exchangeName, "lazy.error", message5);
        rabbitTemplate.convertAndSend(exchangeName, "lazy", message6);
    }
}
