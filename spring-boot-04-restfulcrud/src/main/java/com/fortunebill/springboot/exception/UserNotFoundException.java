package com.fortunebill.springboot.exception;

/**
 * @author Kavin
 * @date 2021年11月16日13:49:00
 */
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("用户未找到");
    }
}
