package com.fortunebill.starter;

/**
 * @author Kavin
 * @date 2021年11月18日15:45:44
 */
public class HelloService {

    HelloProperties helloProperties;

    public HelloService() {
    }

    public HelloService(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHello(String name) {
        return helloProperties.getPrefix() + "【" + name + "】" + helloProperties.getSuffix();
    }
}
