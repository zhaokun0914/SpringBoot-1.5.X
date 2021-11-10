package com.fortunebill.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kavin
 * @date 2021年11月9日21:25:37
 */
@RestController
public class HelloController {

    @Value("${dog.name}")
    private String name;

    @RequestMapping("hello")
    public String sayHello() {
        return name + "hello";
    }

}
