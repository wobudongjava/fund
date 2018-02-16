package com.wo.bu.dong.sample.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wo.bu.dong.common.base.BaseResp;
import com.wo.bu.dong.common.validation.ValidationUtils;
import com.wo.bu.dong.sample.req.CarReq;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SampleService {
    @Value("${name:World}")
    private String name;

    public String getHelloMessage() {
        log.trace("trace message:" + name);
        log.debug("debug message:" + name);
        log.info("info message:" + name);
        log.warn("warn message:" + name);
        log.error("error message:" + name);
        return "Hello " + this.name;
    }

    public BaseResp carIsValid(CarReq car) {
        BaseResp validateResult = ValidationUtils.validateReqParam(car);
        if (validateResult != null) {
            return validateResult;
        }
        return new BaseResp("car is valid");
    }

    public BaseResp carIsNotValid(CarReq car) {
        BaseResp validateResult = ValidationUtils.validateReqParam(car);
        if (validateResult != null) {
            return validateResult;
        }
        return new BaseResp();
    }
}
