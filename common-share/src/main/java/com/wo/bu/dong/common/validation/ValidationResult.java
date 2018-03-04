package com.wo.bu.dong.common.validation;

import com.wo.bu.dong.common.base.BaseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResult extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String            errorMessage;
    private String            errorField;

    public ValidationResult(String errorField, String errorMessage) {
        super();
        this.errorField = errorField;
        this.errorMessage = errorMessage;
    }

    public String getFormatedMessage() {
        return errorField + ":" + errorMessage;
    }
}
