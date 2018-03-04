package com.wo.bu.dong.common.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO<D> extends BaseDTO {
    private static final long  serialVersionUID = 1L;
    private BaseFinalStateEnum status;               //处理结果状态
    private String             code;                 //业务应答码
    private String             message;              //业务描述
    private String             detail;               //业务描述详细信息
    private D                  data;                 //数据

    public ResultDTO() {
        this.status = BaseFinalStateEnum.SUCCESS;
        this.code = BaseCodeEnum.SUCCESS.getCode();
        this.message = BaseCodeEnum.SUCCESS.getMessage();
    }

    public ResultDTO(BaseFinalStateEnum status) {
        this.status = status;
    }

    public ResultDTO(BaseFinalStateEnum status, BaseEnum code) {
        this.status = status;
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ResultDTO(BaseFinalStateEnum status, BaseEnum code, String detail) {
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
        return BaseFinalStateEnum.SUCCESS.getCode().equals(this.status.getCode());
    }

    public static <D> ResultDTO<D> getResultOfSysException(String detail) {
        return new ResultDTO<D>(BaseFinalStateEnum.EXCEPTION, BaseCodeEnum.SYS_EXCEPTION, detail);
    }

    public static <D> ResultDTO<D> getResultOfParamException(String detail) {
        return new ResultDTO<D>(BaseFinalStateEnum.EXCEPTION, BaseCodeEnum.PARAM_EXCEPTION, detail);
    }

}
