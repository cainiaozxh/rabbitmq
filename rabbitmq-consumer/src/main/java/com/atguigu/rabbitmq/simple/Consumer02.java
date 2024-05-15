package com.atguigu.rabbitmq.simple;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.simple
 * @CLASSNAME: Consumer02
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 20:30
 * @SINCE 17.0.7
 * @DESCRIPTION: 工作队列模式,消费者 Consumer02
 * 说明:
 * 工作队列模式 与 入门程序的 简单模式相比 多了一个或者一些消费端,多个消费端共同消费同一个队列中的消息
 * 应用场景:
 * 对于任务过重或任务较多情况使用工作队列可以提高粪污处理的速度
 */
public class Consumer02 {
    public static void main(String[] args) {
        String queueName = "simple_queue";
        try {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            //消费者不用绑定交换机,生产者绑定就可以了
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                /**
                 * No-op implementation of {@link Consumer#handleDelivery}.
                 *
                 * @param consumerTag
                 * @param envelope
                 * @param properties
                 * @param body
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("envelope = " + envelope);
                    System.out.println("body = " + new String(body));
                    System.out.println("==========================");
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
