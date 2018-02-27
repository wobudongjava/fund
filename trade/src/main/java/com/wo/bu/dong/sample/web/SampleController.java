package com.wo.bu.dong.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.common.base.BaseRespDTO;
import com.wo.bu.dong.common.exception.LockException;
import com.wo.bu.dong.common.lock.BusinessPrefixEnum;
import com.wo.bu.dong.common.lock.MyLock;
import com.wo.bu.dong.sample.dto.SampleDTO;
import com.wo.bu.dong.sample.req.CarReq;
import com.wo.bu.dong.sample.resp.CarResp;
import com.wo.bu.dong.sample.service.SampleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("sample")
public class SampleController {
    @Autowired
    private SampleService sampleService;
    @Autowired
    private MyLock        lock;

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
    public SampleDTO sampleTypeNull() {
        log.info("sampleType==> begin");
        SampleDTO result = new SampleDTO();
        log.info("sampleType, result={}", result);
        log.info("sampleType==> end");
        return result;
    }

    @GetMapping("data/notNull")
    @ResponseBody
    public SampleDTO sampleType() {
        log.info("sampleType==> begin");
        SampleDTO result = new SampleDTO();
        result.init();
        log.info("sampleType, result={}", result);
        log.info("sampleType==> end");
        return result;
    }

    @GetMapping("validation/valid")
    @ResponseBody
    public CarResp carIsValid() {
        CarReq car = new CarReq("Sunny", "DD-AB-123", 2);
        log.info("carIsValid==> begin, params={}", car);
        CarResp result = null;
        try {
            result = sampleService.carIsValid(car);
        } catch (Exception e) {
            log.error("carIsValid, 异常", e);
            result = new CarResp();
            result.setResultOfSysException(e.getMessage());
        }
        log.info("carIsValid, result={}", result);
        log.info("carIsValid==> end");
        return result;
    }

    @GetMapping("validation/invalid")
    @ResponseBody
    public CarResp carIsInvalid() {
        CarReq car = new CarReq("Sunny", "D", 4);
        log.info("carIsInvalid==> begin, params={}", car);
        CarResp result = null;
        try {
            result = sampleService.carIsInvalid(car);
        } catch (Exception e) {
            log.error("carIsInvalid, 异常", e);
            result = new CarResp();
            result.setResultOfSysException(e.getMessage());
        }
        log.info("carIsInvalid, result={}", result);
        log.info("carIsInvalid==> end");
        return result;
    }

    @GetMapping("lock")
    @ResponseBody
    public BaseRespDTO lock() {
        log.info("lock==> begin");
        BaseRespDTO result = null;
        String uniqueKey = System.currentTimeMillis() + "";
        boolean isLocked = false;
        try {
            //加锁
            String value = "value_" + uniqueKey;
            isLocked = lock.tryLock(BusinessPrefixEnum.SAMPLE, uniqueKey, value);
            if (!isLocked) {
                throw new LockException("未获取到锁");
            }
            result = new BaseRespDTO();
            result.setDetail("加锁成功");
        } catch (Exception e) {
            log.error("lock, 异常", e);
            result = BaseRespDTO.getResultOfSysException(e.getMessage());
        } finally {
            //解锁
            if (isLocked) {
                lock.unlock(BusinessPrefixEnum.SAMPLE, uniqueKey);
            }
        }
        log.info("lock, result={}", result);
        log.info("lock==> end");
        return result;
    }

}
