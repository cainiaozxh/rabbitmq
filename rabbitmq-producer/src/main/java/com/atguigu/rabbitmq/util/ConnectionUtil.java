package com.atguigu.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.util
 * @CLASSNAME: ConnectionUtil
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 16:33
 * @SINCE 17.0.7
 * @DESCRIPTION: ConnectionUtil
 */
public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            //创建ConnectionFactory
            ConnectionFactory connectionFactory = new ConnectionFactory();
            //主机地址
            connectionFactory.setHost("192.168.74.200");
            //连接端口
            connectionFactory.setPort(5672);
            //虚拟主机名称
            connectionFactory.setVirtualHost("/");
            //连接用户名; 默认为guest
            connectionFactory.setUsername("admin");
            //连接密码: 默认为guest
            connectionFactory.setPassword("admin");

            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConn(Connection connection, Channel channel) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("关闭连接失败");
            throw new RuntimeException(e);
        }
    }
}
