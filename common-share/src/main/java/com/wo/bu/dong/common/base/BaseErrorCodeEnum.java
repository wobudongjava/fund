package com.wo.bu.dong.common.base;

public enum BaseErrorCodeEnum implements BaseEnum {
    PARAM_EXCEPTION("001", "参数异常");
    private String code;
    private String message;

    private BaseErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
