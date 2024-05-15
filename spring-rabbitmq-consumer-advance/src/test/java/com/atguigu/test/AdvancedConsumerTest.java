package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @PACKAGE_NAME: com.atguigu.test
 * @CLASSNAME: AdvancedConsumerTest
 * @AUTHOR: zhangsan
 * @DATE: 2024/5/14 11:09
 * @SINCE 17.0.7
 * @DESCRIPTION: AdvancedConsumerTest
 */
@SpringJUnitConfig(locations = {"classpath:spring-rabbitmq.xml"})
public class AdvancedConsumerTest {
    @Test
    public void test() {
        while (true) {

        }
    }
}
