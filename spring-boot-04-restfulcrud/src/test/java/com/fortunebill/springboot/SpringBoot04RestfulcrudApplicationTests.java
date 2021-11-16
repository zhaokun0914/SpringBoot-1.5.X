package com.fortunebill.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot04RestfulcrudApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void test() {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        System.out.println(status);
    }
}
