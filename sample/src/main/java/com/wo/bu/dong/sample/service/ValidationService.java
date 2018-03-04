package com.wo.bu.dong.sample.service;

import org.springframework.stereotype.Service;

import com.wo.bu.dong.common.base.ResultDTO;
import com.wo.bu.dong.common.validation.ValidationResult;
import com.wo.bu.dong.common.validation.ValidationUtils;
import com.wo.bu.dong.sample.dto.CarDTO;
import com.wo.bu.dong.sample.req.CarReq;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

    public ResultDTO<CarDTO> carIsValid(CarReq car) {
        log.info("carIsValid==> begin, params={}", car);
        ValidationResult validateResult = ValidationUtils.validateReqParam(car);
        if (validateResult != null) {
            log.warn("carIsValid, validateResult={}", validateResult);
            return ResultDTO.getResultOfParamException(validateResult.getFormatedMessage());
        }
        ResultDTO<CarDTO> result = new ResultDTO<>();
        result.setResult("car is valid");
        log.info("carIsValid==> end");
        return result;
    }

    public ResultDTO<CarDTO> carIsInvalid(CarReq car) {
        log.info("carIsInvalid==> begin, params={}", car);
        ValidationResult validateResult = ValidationUtils.validateReqParam(car);
        if (validateResult != null) {
            log.warn("carIsInvalid, validateResult={}", validateResult);
            return ResultDTO.getResultOfParamException(validateResult.getFormatedMessage());
        }
        ResultDTO<CarDTO> result = new ResultDTO<CarDTO>();

        log.info("carIsInvalid==> end");
        return result;
    }
}
