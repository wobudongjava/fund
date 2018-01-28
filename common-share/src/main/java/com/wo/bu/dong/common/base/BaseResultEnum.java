package com.wo.bu.dong.common.base;

public enum BaseResultEnum implements BaseEnum {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    EXCEPTION("EXCEPTION", "异常"),
    PROCESSING("PROCESSING", "处理中");

    private String code;
    private String message;

    private BaseResultEnum(String code, String message) {
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
