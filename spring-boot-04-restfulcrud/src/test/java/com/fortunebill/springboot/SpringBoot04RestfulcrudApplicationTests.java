package com.fortunebill.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot04RestfulcrudApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void test() {
        String[] Converter = context.getBeanNamesForType(Converter.class);
        System.out.println(Converter.length);
        String[] GenericConverter = context.getBeanNamesForType(GenericConverter.class);
        System.out.println(GenericConverter.length);
        String[] Formatter = context.getBeanNamesForType(Formatter.class);
        System.out.println(Formatter.length);
    }

}
