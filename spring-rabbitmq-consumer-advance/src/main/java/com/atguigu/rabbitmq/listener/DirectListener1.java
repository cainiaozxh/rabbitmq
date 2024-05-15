package com.atguigu.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.listener
 * @CLASSNAME: DirectListener1
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/14 10:35
 * @SINCE 17.0.7
 * @DESCRIPTION: DirectListener1
 */
public class DirectListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.printf("DeliveryTag: %s,接收路由名称为: %s,路由键为: %s,队列名为: %s 的消息: %s \n",
                message.getMessageProperties().getDeliveryTag(),
                message.getMessageProperties().getReceivedExchange(),
                message.getMessageProperties().getReceivedRoutingKey(),
                message.getMessageProperties().getConsumerQueue(),
                body);

        int i = 10/0;

    }
}
