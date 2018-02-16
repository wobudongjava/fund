package com.wo.bu.dong.common.base;

import lombok.Getter;

@Getter
public class BaseResp extends BaseDto {
    private static final long serialVersionUID = 1L;

    private BaseResultEnum    status;               //处理结果状态
    private String            code;                 //业务应答码
    private String            message;              //业务描述
    private String            detail;               //业务描述详细信息

    public BaseResp() {
        this.status = BaseResultEnum.SUCCESS;
        this.code = "000";
    }

    public BaseResp(BaseResultEnum status) {
        this.status = status;
    }

    public BaseResp(BaseResultEnum status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public BaseResp(BaseEnum baseEnum) {
        this();
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public BaseResp(BaseEnum baseEnum, String detail) {
        this();
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public BaseResp(String detail) {
        this();
        this.detail = detail;
    }

    public BaseResp(BaseResultEnum status, BaseEnum baseEnum) {
        this();
        this.status = status;
        this.message = baseEnum.getMessage();
    }

    public BaseResp(BaseResultEnum status, BaseEnum baseEnum, String detail) {
        this();
        this.status = status;
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.detail = detail;
    }

    public void setResult(BaseResultEnum status) {
        this.status = status;
    }

    public void setResult(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public void setResult(BaseEnum baseEnum, String detail) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.detail = detail;
    }

    public void setResult(BaseResultEnum status, BaseEnum baseEnum) {
        this.status = status;
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public void setResult(BaseResultEnum status, BaseEnum baseEnum, String detail) {
        this.status = status;
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.detail = detail;
    }

    public void setstatus(BaseResultEnum status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
