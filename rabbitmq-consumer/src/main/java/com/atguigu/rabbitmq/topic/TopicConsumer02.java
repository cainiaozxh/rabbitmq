package com.atguigu.rabbitmq.topic;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import javax.swing.event.MouseInputAdapter;
import java.io.IOException;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.topic
 * @CLASSNAME: TopicConsumer02
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/13 11:25
 * @SINCE 17.0.7
 * @DESCRIPTION: 统配模式 消息的消费者 TopicConsumer02
 */
public class TopicConsumer02 {
    public static void main(String[] args) {
        String queueName = "queue_topic_2";
        try {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //如果该队列不存在,则创建该声明该队列
            channel.queueDeclare(queueName, true, false, false, null);
            //消费者类似一个监听程序,主要用来监听消息

            channel.basicConsume(queueName, true, new DefaultConsumer(channel){
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
                            System.out.println("=============queue1Name=============");
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
