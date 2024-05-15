package com.atguigu.rabbitmq.routing;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.routing
 * @CLASSNAME: RoutingProducer
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/13 10:43
 * @SINCE 17.0.7
 * @DESCRIPTION: RoutingProducer 路由模式 消息的生产者
 */
public class RoutingProducer {
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;

        try {
            //1 获取连接
            connection = ConnectionUtil.getConnection();
            //2 获取信道
            channel = connection.createChannel();
            //3 信道创建交换机
            String exchangeName = "exchange_direct";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);
            //4 信道创建队列
            String queue1Name = "queue_direct_1";
            String queue2Name = "queue_direct_2";
            channel.queueDeclare(queue1Name, true, false, false, null);
            channel.queueDeclare(queue2Name, true, false, false, null);

            //5 交换机绑定队列
            //交换机绑定队列1的路由键 error
            channel.queueBind(queue1Name, exchangeName, "error");
            //交换机绑定队列2的路由键 info error warning
            channel.queueBind(queue2Name, exchangeName, "info");
            channel.queueBind(queue2Name, exchangeName, "error");
            channel.queueBind(queue2Name, exchangeName, "warning");

            //发送消息
            String errorMsg = "Hello 路由模式, hello direct,routingKey = error";
            String infoMsg = "Hello 路由模式, hello direct, routingKey = info";
            String warningMsg = "Hello 路由模式, hello direct, routingKey = warning";
            channel.basicPublish(exchangeName, "error", false, null,
                    errorMsg.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(exchangeName, "info", false, null,
                    infoMsg.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(exchangeName, "warning", false, null,
                    warningMsg.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.closeConn(connection, channel);
        }
    }
}
