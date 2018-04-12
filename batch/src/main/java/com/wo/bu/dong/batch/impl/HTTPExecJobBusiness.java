package com.wo.bu.dong.batch.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wo.bu.dong.batch.api.ExecJobBusiness;
import com.wo.bu.dong.common.http.HTTPUtils;
import com.wo.bu.dong.common.http.HttpReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HTTPExecJobBusiness implements ExecJobBusiness {
    private Object businessParam;

    @Override
    public void execute() {
        HttpReq req = new HttpReq("http://www.baidu.com", null, null, "utf-8");
        String result = HTTPUtils.get(req);
        log.info("execute, result:{}", result);
    }

    @Override
    public void setBusinessParam(Object businessParam) {
        this.businessParam = businessParam;
    }

}
