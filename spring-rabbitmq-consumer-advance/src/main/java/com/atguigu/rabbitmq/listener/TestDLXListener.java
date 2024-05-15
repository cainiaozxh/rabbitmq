package com.atguigu.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.nio.charset.StandardCharsets;

/**
 * @PACKAGE_NAME: com.atguigu.rabbitmq.listener
 * @CLASSNAME: TestDLXListener
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/15 10:14
 * @SINCE 17.0.7
 * @DESCRIPTION: TestDLXListener
 */
public class TestDLXListener implements ChannelAwareMessageListener {
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

            int i = 3/0;
            //没有异常的处理逻辑

            //3 手动签收
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //有异常的处理逻辑
            //System.out.println(e);
            System.out.println("出现异常,拒绝接受");
            //4拒绝签收,不重回队列 requeue=false
            channel.basicNack(deliveryTag, false, false);
            throw new RuntimeException(e);
        }
    }
}
