package com.fortunebill.springboot.controller;

import com.fortunebill.springboot.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Kavin
 * @date 2021年11月11日14:57:50
 */
@Controller
@Slf4j
public class HelloController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello(String username) {
        if ("aaa".equals(username)) {
            throw new UserNotFoundException();
        }
        return "Hello World";
    }

    @RequestMapping("success")
    public String success(Map<String, Object> hashMap) {
        log.info("==> 进入success 方法了");
        // 查出一些数据在页面展示
        hashMap.put("hello", "<h1>你好</h1>");
        hashMap.put("users", Arrays.asList("张三", "李四", "王五"));
        return "success";
    }

}
