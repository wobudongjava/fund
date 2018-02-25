package com.wo.bu.dong.common.base;

public enum BaseCodeEnum implements BaseEnum {
    SUCCESS("000", "成功"),
    PARAM_EXCEPTION("001", "参数异常"),
    SYS_EXCEPTION("002", "系统异常");
    private String code;
    private String message;

    private BaseCodeEnum(String code, String message) {
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
