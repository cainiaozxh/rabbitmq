package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @PACKAGE_NAME: com.atguigu.test
 * @CLASSNAME: ConsumerTest
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/13 17:18
 * @SINCE 17.0.7
 * @DESCRIPTION: ConsumerTest
 */
@SpringJUnitConfig(locations = {"classpath:spring-rabbitmq.xml"})
public class ConsumerTest {
    @Test
    public void simpleQueue() {
        while (true) {

        }
    }
}
