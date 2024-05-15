package com.atguigu.rabbitmq.fanout;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.fanout
 * @CLASSNAME: FanoutConsumer02
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 22:04
 * @SINCE 17.0.7
 * @DESCRIPTION: 发布订阅模式 消息的消费者 FanoutConsumer02
 */
public class FanoutConsumer02 {
    public static void main(String[] args) {
        String queue2Name = "queue_fanout_2";
        try {
            Connection connection = ConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //如果该队列不存在,则创建该声明该队列
            channel.queueDeclare(queue2Name, true, false, false, null);
            //消费者类似一个监听程序,主要用来监听消息

            channel.basicConsume(queue2Name, true, new DefaultConsumer(channel){
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
                            System.out.println("============queue2Name==============");
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
