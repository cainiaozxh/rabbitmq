package com.atguigu.rabbitmq.simple;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.simple
 * @CLASSNAME: Producer
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 10:43
 * @SINCE 17.0.7
 * @DESCRIPTION: 工作队列生产者
 * 简单模式: Hello rabbitma
 * 工作队列使用的也是 默认交换机
 */
public class Producer {
    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        //创建Connection
        try {
            connection = ConnectionUtil.getConnection();
            //创建Channel
            channel = connection.createChannel();
            //创建Exchange(简单模式该步骤省略,使用默认交换机(AMQP default))

            //声明队列Queue
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //建立Exchange和Queue之间的Binding关系
            // 编辑消息内容
            String message = "Hello,rabbitmq, 你好 rabbitmq";
            for (int i = 0; i < 10; i++) {
                String body = message + "-" + i;
                channel.basicPublish("", QUEUE_NAME, null, body.getBytes(StandardCharsets.UTF_8));
                System.out.println("已发送消息: " + body);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
