package com.wo.bu.dong.common.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.wo.bu.dong.common.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtils {
    private static final Validator VALIDATOR;
    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * @param param 待校验参数
     * @return null 校验通过，not null 校验不通过
     */
    public static <T> ValidationResult validateReqParam(T param) {
        Set<ConstraintViolation<T>> constraintViolations = null;
        ValidationResult result = null;
        try {
            constraintViolations = VALIDATOR.validate(param);
        } catch (Exception e) {
            log.error("参数校验异常：{}", e.getMessage(), e);
            throw new SystemException("参数校验异常", e);
        }
        if (constraintViolations.size() == 0) {
            return null;
        }
        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<T> cv = iterator.next();
            result = new ValidationResult(cv.getPropertyPath().toString(), cv.getMessage());
            break;
        }
        return result;
    }
}
