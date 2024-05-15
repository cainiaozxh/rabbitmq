package com.atguigu.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.util
 * @CLASSNAME: ConnectionUtil
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/12 20:29
 * @SINCE 17.0.7
 * @DESCRIPTION: 获取连接工具类 ConnectionUtil
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
}
