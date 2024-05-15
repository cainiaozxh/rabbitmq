package com.atguigu.rabbitmq.topic;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.topic
 * @CLASSNAME: TopicProducer
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/13 11:23
 * @SINCE 17.0.7
 * @DESCRIPTION: 通配模式 消息的生产者 TopicProducer
 */
public class TopicProducer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        //1 获取连接
        try {
            connection = ConnectionUtil.getConnection();
            //2 获取信道
            channel = connection.createChannel();
            //3 声明交换机
            String exchangeName = "exchange_topic";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, false,null);
            //4 声明队列
            String queue1Name = "queue_topic_1";
            String queue2Name = "queue_topic_2";
            channel.queueDeclare(queue1Name, true, false, false, null);
            channel.queueDeclare(queue2Name, true, false, false, null);
            //5 交换机绑定队列
            channel.queueBind(queue1Name, exchangeName, "*.orange.*");
            channel.queueBind(queue1Name, exchangeName, "#.error");
            channel.queueBind(queue2Name, exchangeName, "order.*");
            channel.queueBind(queue2Name, exchangeName, "*.*.rabbit");
            channel.queueBind(queue2Name, exchangeName, "lazy.#");

            //6 发送消息
            String message1 = "hello topic, bindingKey = *.orange.* -message1";
            String message2 = "hello topic, bindingKey = #.error -message2";
            String message3 = "hello topic, bindingKey = order.* -message3";
            String message4 = "hello topic, bindingKey = *.*.rabbit -message4";
            String message5 = "hello topic, bindingKey = lazy.# -message5";
            String message6 = "hello topic, bindingKey = lazy.# -message6";

            // 1 2
            channel.basicPublish(exchangeName, "a.orange.rabbit", false, null, message1.getBytes(StandardCharsets.UTF_8));
            //1 2
            channel.basicPublish(exchangeName, "order.error", false, null, message2.getBytes(StandardCharsets.UTF_8));
            //1 2
            channel.basicPublish(exchangeName, "order.error", false, null, message3.getBytes(StandardCharsets.UTF_8));
            //2
            channel.basicPublish(exchangeName, "a.b.rabbit", false, null, message4.getBytes(StandardCharsets.UTF_8));
            //1 2
            channel.basicPublish(exchangeName, "lazy.error", false, null, message5.getBytes(StandardCharsets.UTF_8));
            //2
            channel.basicPublish(exchangeName, "lazy", false, null, message6.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.closeConn(connection, channel);
        }
    }
}
