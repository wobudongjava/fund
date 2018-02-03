package com.wo.bu.dong.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.sample.dto.SampleDto;
import com.wo.bu.dong.sample.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("sample")
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

    @GetMapping("data/null")
    @ResponseBody
    public SampleDto sampleTypeNull() {
        log.info("sampleType==> begin");
        SampleDto result = new SampleDto();
        log.info("sampleType, result={}", result);
        log.info("sampleType==> end");
        return result;
    }

    @GetMapping("data/notNull")
    @ResponseBody
    public SampleDto sampleType() {
        log.info("sampleType==> begin");
        SampleDto result = new SampleDto();
        result.init();
        log.info("sampleType, result={}", result);
        log.info("sampleType==> end");
        return result;
    }

}
