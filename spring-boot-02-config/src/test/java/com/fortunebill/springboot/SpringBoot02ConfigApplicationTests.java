package com.fortunebill.springboot;

import com.fortunebill.springboot.bean.Dog;
import com.fortunebill.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试
 *
 * 可以在测试期间很方便的类似编码一样进行自动注入
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {

    @Autowired
    private Person person;
    @Autowired
    private Dog dog;

    @Test
    public void test() {
        System.out.println(person.toString());
        System.out.println(dog.toString());
    }
}
