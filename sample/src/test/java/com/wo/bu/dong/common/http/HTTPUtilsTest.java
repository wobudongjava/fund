package com.wo.bu.dong.common.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.junit.Assert;
import org.junit.Test;

import com.wo.bu.dong.sample.req.CarReq;

public class HTTPUtilsTest {

    @Test
    public void getTest() {
        HttpReq req = new HttpReq("http://localhost:8080/sample/data/null");
        Assert.assertNotNull(HTTPUtils.get(req));
    }

    @Test
    public void postTest() {
        HttpReq req = new HttpReq("http://localhost:8080/sample/data/notNull");
        Assert.assertNotNull(HTTPUtils.post(req));
        CarReq car = new CarReq("Sunny", "DD-AB-123", 2);
        Map<String, Object> params = new HashMap<>();
        BeanMap beanMap = new BeanMap(car);
        for (Object key : beanMap.keySet()) {
            if ("class".equals(key))
                continue;
            params.put(key + "", beanMap.get(key));
        }
        req = new HttpReq("http://localhost:8080/sample/validation/valid", HTTPUtils.CONTENTTYPE_APPLICATION_JSON, params);
        Assert.assertNotNull(HTTPUtils.post(req));
    }

}
