package com.wo.bu.dong.sample.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wo.bu.dong.common.base.BaseRespDTO;
import com.wo.bu.dong.common.config.RedisConfig;
import com.wo.bu.dong.common.validation.ValidationUtils;
import com.wo.bu.dong.sample.req.CarReq;
import com.wo.bu.dong.sample.resp.CarResp;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SampleService {
    @Value("${name:World}")
    private String      name;
    @Autowired
    private RedisConfig redisConfig;

    public String getHelloMessage() {
        System.out.println(redisConfig);
        log.trace("trace message:" + name);
        log.debug("debug message:" + name);
        log.info("info message:" + name);
        log.warn("warn message:" + name);
        log.error("error message:" + name);
        return "Hello " + this.name;
    }

    public CarResp carIsValid(CarReq car) {
        log.info("carIsValid==> begin, params={}", car);
        BaseRespDTO validateResult = ValidationUtils.validateReqParam(car);
        CarResp result = new CarResp();
        if (validateResult != null) {
            log.warn("carIsValid, validateResult={}", validateResult);
            BeanUtils.copyProperties(validateResult, result);
            return result;
        }
        result.setResult("car is valid");
        log.info("carIsValid==> end");
        return result;
    }

    public CarResp carIsInvalid(CarReq car) {
        log.info("carIsInValid==> begin, params={}", car);
        BaseRespDTO validateResult = ValidationUtils.validateReqParam(car);
        CarResp result = new CarResp();
        if (validateResult != null) {
            log.warn("carIsValid, validateResult={}", validateResult);
            BeanUtils.copyProperties(validateResult, result);
            return result;
        }
        log.info("carIsInValid==> end");
        return result;
    }
}
