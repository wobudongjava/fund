package com.wo.bu.dong.common.base;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseReqDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    //请求流水号（非业务字段）
    private String            reqNo            = UUID.randomUUID().toString().replaceAll("-", "");

}
