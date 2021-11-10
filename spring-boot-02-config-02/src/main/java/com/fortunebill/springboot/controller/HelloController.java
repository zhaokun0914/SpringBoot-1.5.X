package com.fortunebill.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kavin
 * @date 2021年11月10日10:36:31
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

}
