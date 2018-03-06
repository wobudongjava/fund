package com.wo.bu.dong.sample.web.mock;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 只启动应用程序上下文，没有启动服务器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloWorld() throws Exception {
        String url = "/sample";
        mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Hello")));
    }

}
