package com.atguigu.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.listener
 * @CLASSNAME: QosListener
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/14 15:49
 * @SINCE 17.0.7
 * @DESCRIPTION: QosListener
 */
public class QosListener implements ChannelAwareMessageListener {
    /**
     * @param message the received AMQP message (never <code>null</code>)
     * @param channel the underlying Rabbit Channel (never <code>null</code>)
     * @throws Exception Any.
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            String s = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(s);

            //没有异常的处理逻辑
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //有异常的处理逻辑
            System.out.println(e);
            channel.basicNack(deliveryTag, false, true);
            throw new RuntimeException(e);
        }
    }
}
