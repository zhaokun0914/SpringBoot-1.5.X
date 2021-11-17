package com.fortunebill.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kavin
 * @date 2021年11月17日11:28:18
 */
@Controller
public class HelloController {

    @GetMapping("/abc")
    public String hello(Model map) {
        map.addAttribute("message", "你好");
        return "success";
    }

}
