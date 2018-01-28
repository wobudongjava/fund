package com.wo.bu.dong.common.base;

import lombok.Getter;

@Getter
public class BaseResp {
    private String code;
    private String message;
    private String detail;

    public BaseResp(BaseEnum baseEnum) {
        super();
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public BaseResp(BaseEnum baseEnum, String detail) {
        super();
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.detail = detail;
    }

    public BaseResp() {
        super();
    }

    public void setResult(BaseEnum baseEnum, String detail) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.detail = detail;
    }

    public void setResult(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }
}
