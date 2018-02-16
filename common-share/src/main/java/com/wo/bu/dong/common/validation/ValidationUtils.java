package com.wo.bu.dong.common.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.wo.bu.dong.common.base.BaseErrorCodeEnum;
import com.wo.bu.dong.common.base.BaseResp;
import com.wo.bu.dong.common.base.BaseResultEnum;

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
    public static <T> BaseResp validateReqParam(T param) {
        Set<ConstraintViolation<T>> constraintViolations = null;
        try {
            constraintViolations = VALIDATOR.validate(param);
        } catch (Exception e) {
            log.warn("参数校验异常：{},[{}]", e.getMessage(), e);
            return new BaseResp(BaseResultEnum.EXCEPTION, BaseErrorCodeEnum.PARAM_EXCEPTION, e.getMessage());
        }
        if (constraintViolations.size() == 0) {
            return null;
        }
        BaseResp result = new BaseResp(BaseResultEnum.EXCEPTION);
        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<T> cv = iterator.next();
            result.setResult(BaseErrorCodeEnum.PARAM_EXCEPTION, cv.getMessage());
            break;
        }
        return result;
    }
}
