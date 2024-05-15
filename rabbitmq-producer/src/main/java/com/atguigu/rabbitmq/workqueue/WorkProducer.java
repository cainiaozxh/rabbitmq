package com.atguigu.rabbitmq.workqueue;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.workqueue
 * @CLASSNAME: WorkProducer
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 20:32
 * @SINCE 17.0.7
 * @DESCRIPTION: WorkProducer 工作队列模式生产者
 * 工作队列 也使用默认交换机
 * The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
 */
public class WorkProducer {
    public static void main(String[] args) {
        String queueName = "work_queue";
        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtil.getConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            //不用绑定交换机,使用默认交换机
            String baseMessage = "Hello,rabbitmq, hello 工作队列";
            for (int i = 0; i < 10; i++) {
                String message = baseMessage + "-" + i;
                //简单模式 工作队列模式 的 routingKey 路由键和队列名称一致
                channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("消息已经全部发送");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.closeConn(connection, channel);
        }
    }
}
