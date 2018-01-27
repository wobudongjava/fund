package com.wo.bu.dong.trade.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.trade.sample.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        log.info("helloWorld==> begin");
        String result = this.sampleService.getHelloMessage();
        log.info("helloWorld==> end");
        return result;
    }
}
