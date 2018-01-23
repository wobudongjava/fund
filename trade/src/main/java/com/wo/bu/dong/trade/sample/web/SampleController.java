package com.wo.bu.dong.trade.sample.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.trade.sample.service.SampleService;

@Controller
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return this.sampleService.getHelloMessage();
    }
}
