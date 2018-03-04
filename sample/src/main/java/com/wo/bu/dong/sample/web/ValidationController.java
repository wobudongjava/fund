package com.wo.bu.dong.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.common.base.ResultDTO;
import com.wo.bu.dong.sample.dto.CarDTO;
import com.wo.bu.dong.sample.req.CarReq;
import com.wo.bu.dong.sample.service.ValidationService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("validation")
public class ValidationController {
    @Autowired
    private ValidationService validationService;

    @GetMapping("valid")
    @ResponseBody
    public ResultDTO<CarDTO> carIsValid() {
        CarReq car = new CarReq("Sunny", "DD-AB-123", 2);
        log.info("carIsValid==> begin, params={}", car);

        ResultDTO<CarDTO> result = null;
        try {
            result = validationService.carIsValid(car);
        } catch (Exception e) {
            log.error("carIsValid, 异常", e);
            return ResultDTO.getResultOfSysException(e.getMessage());
        }

        log.info("carIsValid, result={}", result);
        log.info("carIsValid==> end");
        return result;
    }

    @GetMapping("invalid")
    @ResponseBody
    public ResultDTO<CarDTO> carIsInvalid() {
        CarReq car = new CarReq("Sunny", "D", 4);
        log.info("carIsInvalid==> begin, params={}", car);

        ResultDTO<CarDTO> result = null;
        try {
            result = validationService.carIsInvalid(car);
        } catch (Exception e) {
            log.error("carIsInvalid, 异常", e);
            return ResultDTO.getResultOfSysException(e.getMessage());
        }

        log.info("carIsInvalid, result={}", result);
        log.info("carIsInvalid==> end");
        return result;
    }

}
