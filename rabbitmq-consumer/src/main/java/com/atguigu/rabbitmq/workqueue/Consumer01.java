package com.atguigu.rabbitmq.workqueue;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.workqueue
 * @CLASSNAME: Consumer01
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 20:27
 * @SINCE 17.0.7
 * @DESCRIPTION: 工作队列模式的 Consumer01
 */
public class Consumer01 {
    private static final String QUEUE_NAME = "work_queue";
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //如果该队列不存在,则创建该声明该队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //消费者类似一个监听程序,主要用来监听消息

            channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
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
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
