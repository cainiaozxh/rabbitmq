package com.atguigu.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.listener
 * @CLASSNAME: DirectListener2
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/14 10:35
 * @SINCE 17.0.7
 * @DESCRIPTION: DirectListener2
 */
public class ManualAckDirectListener implements ChannelAwareMessageListener {
    /**
     * @param message the received AMQP message (never <code>null</code>)
     * @param channel the underlying Rabbit Channel (never <code>null</code>)
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //1 接收消息
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
            //2 处理业务逻辑
            System.out.println("处理业务逻辑");
            int i = 3/0;
            //3 手动签收
            /**
             * 第一个参数 表示收到的标签
             * 第二个参数 如果为true表示可以签收多有的消息
             */
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //4 拒绝签收
            /**
             * 第三个参数:requeue:重回队列
             * 设置为true,则消息会重新回到queue,broker会重新发送消息给消费者
             */
            channel.basicNack(deliveryTag, false, false);
            throw new RuntimeException(e);
        }
    }
}
