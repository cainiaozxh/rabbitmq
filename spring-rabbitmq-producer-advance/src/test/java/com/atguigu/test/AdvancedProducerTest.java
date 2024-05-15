package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.atguigu.test
 * @CLASSNAME: AdvancedProducerTest
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/14 10:23
 * @SINCE 17.0.7
 * @DESCRIPTION: AdvancedProducerTest
 */
@SpringJUnitConfig(locations = {"classpath:spring-rabbitmq.xml"})
public class AdvancedProducerTest {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void routingQueue() {

        //发送消息
        String errorMsg = "Hello 路由模式, hello direct,routingKey = error";
        String infoMsg = "Hello 路由模式, hello direct, routingKey = info";
        String warningMsg = "Hello 路由模式, hello direct, routingKey = warning";
        String exchangeName = "spring_direct_exchange";
        rabbitTemplate.convertAndSend(exchangeName, "error", errorMsg);
        rabbitTemplate.convertAndSend(exchangeName, "info", infoMsg);
        rabbitTemplate.convertAndSend(exchangeName, "warning", warningMsg);
    }

    /**
     * 只给队列1发消息
     */
    @Test
    public void confirmCallback() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    //接收成功
                    System.out.println("交换机接收消息成功-" + cause);
                } else {
                    //接收失败
                    System.out.println("交换机接收消息失败-" + cause);
                    //做一些处理,让消息再次发送
                }
            }
        });

        //发送消息
        String errorMsg = "Hello 路由模式, hello direct,routingKey = error";
        //String exchangeName = "spring_direct_exchange";
        //成功
        //rabbitTemplate.convertAndSend("spring_direct_exchange", "error", errorMsg);
        //交换机失败
        //rabbitTemplate.convertAndSend("spring_direct_exchange11", "error", errorMsg);
        //队列失败
        rabbitTemplate.convertAndSend("spring_direct_exchange", "error11", errorMsg);
    }

    /**
     * 添加回退模式
     */
    @Test
    public void returnCallback() {
        //设置交换机处理失败消息的模式
        //Mandatory为true时,消息通过交换机无法匹配到队列会返回给生产者false时,匹配不到会直接被丢弃
        rabbitTemplate.setMandatory(true);
        //设置ReturnCallback
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 执行了");

                System.out.println(message);
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
            }
        });

        //发送消息
        String errorMsg = "Hello 路由模式, hello direct,routingKey = error";

        //成功
        rabbitTemplate.convertAndSend("spring_direct_exchange", "error", errorMsg);
        //交换机失败
        //rabbitTemplate.convertAndSend("spring_direct_exchange11", "error", errorMsg);
        //队列失败
        //rabbitTemplate.convertAndSend("spring_direct_exchange", "error11", errorMsg);
    }

    /**
     * 测试消费端限流的 生产者
     */
    @Test
    public void testQosListener() {
        //发送消息
        String errorMsg = "Hello 路由模式, hello direct,routingKey = error";
        for (int i = 0; i < 10; i++) {
            String message = errorMsg + "-" + i;
            //成功
            rabbitTemplate.convertAndSend("spring_direct_exchange", "error", message);
        }
    }

    /**
     * 测试TTL
     */
    @Test
    public void testTTL() {
        //发送消息
        String baseMessage = "队列过期时间为10秒, routingKey = ttl.# ";
        for (int i = 0; i < 5; i++) {
            String message = baseMessage+ "-" + i;
            rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.xxx", message);
        }
    }

    @Test
    public void testDLX() {
        //发送消息
        String baseMessage = "队列过期时间为10秒, routingKey = ttl.# ";
        for (int i = 0; i < 5; i++) {
            String message = baseMessage+ "-" + i;
            rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.haha", message);
        }
    }

    @Test
    public void testLengthDLX() {
        //发送消息
        String baseMessage = "队列过期时间为10秒, routingKey = ttl.# ";
        for (int i = 0; i < 10; i++) {
            String message = baseMessage+ "-" + i;
            rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.haha", message);
        }
    }
}
