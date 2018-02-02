package com.wo.bu.dong.common.base;

import lombok.Getter;

@Getter
public class BaseResp extends BaseDto {
    private static final long serialVersionUID = 1L;

    private BaseResultEnum    resultStatus;         //处理结果状态
    private String            code;                 //业务应答码
    private String            message;              //业务描述
    private String            detail;               //业务描述详细信息

    public BaseResp() {
        resultStatus = BaseResultEnum.SUCCESS;
    }

    public BaseResp(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public BaseResp(BaseResultEnum resultStatus, BaseEnum baseEnum) {
        this.resultStatus = resultStatus;
        this.message = baseEnum.getMessage();
    }

    public BaseResp(BaseResultEnum resultStatus, String code, String message, String detail) {
        this.resultStatus = resultStatus;
        this.code = code;
        this.message = message;
        this.detail = detail;
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

    public void setResult(BaseResultEnum resultStatus, BaseEnum baseEnum) {
        this.resultStatus = resultStatus;
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }

    public void setResult(BaseResultEnum resultStatus, BaseEnum baseEnum, String detail) {
        this.resultStatus = resultStatus;
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
        this.detail = detail;
    }

    public void setResultStatus(BaseResultEnum resultStatus) {
        this.resultStatus = resultStatus;
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
