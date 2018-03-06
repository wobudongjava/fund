package com.wo.bu.dong.sample.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 此配置会启用服务器，在服务器访问日志中可查看请求信息
 * webEnvironment=RANDOM_PORT随机端口启动服务器（用于避免测试环境中的冲突）
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleControllerTest {

    @LocalServerPort
    private int              port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHelloWorld() throws Exception {
        String server = "http://localhost:" + port + "/sample";
        String url = server + "/sample";
        String result = this.testRestTemplate.getForObject(url, String.class);
        Assert.assertTrue(result.contains("Hello"));

    }

}
