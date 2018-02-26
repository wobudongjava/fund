package com.wo.bu.dong.common.base;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasePageRespDTO<T> extends BaseRespDTO {
    private static final long serialVersionUID = 1L;

    private int               pageNum;
    private int               pageSize;
    private long              total;
    private int               pages;
    private boolean           count;
    private List<T>           data;

}
