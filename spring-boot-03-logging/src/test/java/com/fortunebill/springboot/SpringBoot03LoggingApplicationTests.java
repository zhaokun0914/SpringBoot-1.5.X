package com.fortunebill.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot03LoggingApplicationTests {

    //记录器
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBoot03LoggingApplicationTests.class);

    @Test
    public void test() {
        //System.out.println();

        //日志的级别；
        //由低到高 trace < debug < info < warn < error
        //可以调整输出的日志级别；日志就只会在这个级别和更高级别生效
        LOGGER.trace("这是trace日志...");
        LOGGER.debug("这是debug日志...");

        //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
        LOGGER.info("这是info日志...");
        LOGGER.warn("这是warn日志...");
        LOGGER.error("这是error日志...");
    }

}
