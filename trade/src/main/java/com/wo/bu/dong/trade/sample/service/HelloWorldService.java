package com.wo.bu.dong.trade.sample.service;

import org.springframework.beans.factory.annotation.Value;

public class HelloWorldService {
    @Value("${name:World}")
    private String name;

    public String getHelloMessage() {
        return "Hello " + this.name;
    }
}
