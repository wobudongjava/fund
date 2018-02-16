package com.wo.bu.dong.common.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 基于mytatis-pageHelper插件，分页请求 注：pageNum,pageSize = 0 或
 * null->查询所有;pageNum,pageSize等于0时，会查询所有,并返回总记录数
 */
public abstract class BasePageReq extends BaseReq {
    private static final long serialVersionUID = 1L;
    private Integer           pageNum          = 1;
    private Integer           pageSize         = 10;
}
