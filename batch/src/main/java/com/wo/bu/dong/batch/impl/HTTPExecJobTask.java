package com.wo.bu.dong.batch.impl;

import java.util.Map;

import com.wo.bu.dong.batch.api.ExecJobTask;
import com.wo.bu.dong.common.http.HTTPUtils;
import com.wo.bu.dong.common.http.HttpReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HTTPExecJobTask implements ExecJobTask {
    private Map<String, Object> params;

    @Override
    public void execute() {
        HttpReq req = new HttpReq("http://www.baidu.com", null, null, "utf-8");
        String result = HTTPUtils.get(req);
        log.info("execute, result:{}", result);
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
