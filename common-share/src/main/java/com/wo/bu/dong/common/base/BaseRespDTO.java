package com.wo.bu.dong.common.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRespDTO extends BaseDTO {
    private static final long  serialVersionUID = 1L;

    private BaseFinalStateEnum status;               //处理结果状态
    private String             code;                 //业务应答码
    private String             message;              //业务描述
    private String             detail;               //业务描述详细信息

    public BaseRespDTO() {
        this.status = BaseFinalStateEnum.SUCCESS;
        this.code = BaseCodeEnum.SUCCESS.getCode();
        this.message = BaseCodeEnum.SUCCESS.getMessage();
    }

    public BaseRespDTO(BaseFinalStateEnum status) {
        this.status = status;
    }

    public BaseRespDTO(BaseFinalStateEnum status, BaseEnum code) {
        this.status = status;
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public BaseRespDTO(BaseFinalStateEnum status, BaseEnum code, String detail) {
        this.status = status;
        this.code = code.getCode();
        this.message = code.getMessage();
        this.detail = detail;
    }

    public void setResult(BaseFinalStateEnum status) {
        this.status = status;
    }

    public void setResult(BaseEnum code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public void setResult(String detail) {
        this.detail = detail;
    }

    public void setResult(BaseFinalStateEnum status, BaseEnum code) {
        this.status = status;
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public void setResult(BaseFinalStateEnum status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public void setResult(BaseFinalStateEnum status, BaseEnum code, String detail) {
        this.status = status;
        this.code = code.getCode();
        this.message = code.getMessage();
        this.detail = detail;
    }

    public void setResult(BaseEnum code, String detail) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.detail = detail;
    }

    public void setResultOfSysException(String detail) {
        this.status = BaseFinalStateEnum.EXCEPTION;
        this.code = BaseCodeEnum.SYS_EXCEPTION.getCode();
        this.message = BaseCodeEnum.SYS_EXCEPTION.getMessage();
        this.detail = detail;
    }

    public boolean isSuccess() {
        return this.status == BaseFinalStateEnum.SUCCESS;
    }

    public static BaseRespDTO getResultOfSysException(String detail) {
        return new BaseRespDTO(BaseFinalStateEnum.EXCEPTION, BaseCodeEnum.SYS_EXCEPTION, detail);
    }
}
