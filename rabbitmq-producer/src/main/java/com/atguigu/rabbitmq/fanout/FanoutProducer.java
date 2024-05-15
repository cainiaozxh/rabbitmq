package com.atguigu.rabbitmq.fanout;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.fanout
 * @CLASSNAME: FanoutProducer
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 21:29
 * @SINCE 17.0.7
 * @DESCRIPTION: 发布订阅模式 生产者
 * 发布订阅模式 或者说是 广播模式
 */
public class FanoutProducer {
    public static void main(String[] args) {
        String exchangeName = "exchange_fanout";
        Connection connection = null;
        Channel channel = null;
        try {
            //1 获取连接
            connection = ConnectionUtil.getConnection();
            //2 获取信道
            channel = connection.createChannel();
            //3 创建交换机
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);

            //4 声明队列
            String queue1Name = "queue_fanout_1";
            String queue2Name = "queue_fanout_2";
            channel.queueDeclare(queue1Name, true, false, false, null);
            channel.queueDeclare(queue2Name, true, false, false, null);

            //5 交换机与队列绑定
            channel.queueBind(queue1Name, exchangeName, "aaa");
            channel.queueBind(queue2Name, exchangeName, "aaa");

            //6 发送消息
            String baseMessage = "hello rabbitmq, hello fanout";
            for (int i = 0; i < 10; i++) {
                String message = baseMessage + "-" + i;
                channel.basicPublish(exchangeName, "aaa", false, null, message.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.closeConn(connection, channel);
        }

    }
}
