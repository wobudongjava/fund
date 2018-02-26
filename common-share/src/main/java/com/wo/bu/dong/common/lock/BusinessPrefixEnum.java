package com.wo.bu.dong.common.lock;

import com.wo.bu.dong.common.base.BaseEnum;

public enum BusinessPrefixEnum implements BaseEnum {
    ;
    private String code;
    private String message;

    @Override
    public String getCode() {
        return code;
    }

    BusinessPrefixEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
