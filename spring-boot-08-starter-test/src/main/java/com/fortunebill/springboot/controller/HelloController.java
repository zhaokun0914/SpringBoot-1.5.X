package com.fortunebill.springboot.controller;

import com.fortunebill.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kavin
 * @date 2021年11月18日16:14:56
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/")
    public String hello(String username) {
        return helloService.sayHello(username);
    }

}
