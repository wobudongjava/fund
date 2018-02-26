package com.wo.bu.dong.common.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.wo.bu.dong.common.base.BaseCodeEnum;
import com.wo.bu.dong.common.base.BaseFinalStateEnum;
import com.wo.bu.dong.common.base.BaseRespDTO;

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
    public static <T> BaseRespDTO validateReqParam(T param) {
        Set<ConstraintViolation<T>> constraintViolations = null;
        try {
            constraintViolations = VALIDATOR.validate(param);
        } catch (Exception e) {
            log.error("参数校验异常：{}", e.getMessage(), e);
            return new BaseRespDTO(BaseFinalStateEnum.EXCEPTION, BaseCodeEnum.SYS_EXCEPTION, e.getMessage());
        }
        if (constraintViolations.size() == 0) {
            return null;
        }
        BaseRespDTO result = new BaseRespDTO(BaseFinalStateEnum.EXCEPTION, BaseCodeEnum.PARAM_EXCEPTION);
        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<T> cv = iterator.next();
            result.setResult(cv.getPropertyPath() + ":" + cv.getMessage());
            break;
        }
        return result;
    }
}
