package com.fortunebill.springboot.controller;

import com.fortunebill.springboot.entity.User;
import com.fortunebill.springboot.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/query/{id}")
    public User query(@PathVariable("id") Integer id) {
        return userRepository.findOne(id);
    }

    @PostMapping("/save")
    public User save(User user) {
        return userRepository.save(user);
    }

}
