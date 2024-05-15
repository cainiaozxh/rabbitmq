package com.atguigu.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.listener
 * @CLASSNAME: SimpleQueueListener
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/13 17:02
 * @SINCE 17.0.7
 * @DESCRIPTION: SimpleQueueListener
 */
public class SimpleQueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.printf("接收路由名称为: %s,路由键为: %s,队列名为: %s 的消息: %s \n",
                    message.getMessageProperties().getReceivedExchange(),
                    message.getMessageProperties().getReceivedRoutingKey(),
                    message.getMessageProperties().getConsumerQueue(),
                    body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
