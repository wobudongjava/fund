package com.wo.bu.dong.sample.web.mock;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.wo.bu.dong.common.base.ResultDTO;
import com.wo.bu.dong.sample.dto.CarDTO;
import com.wo.bu.dong.sample.req.CarReq;
import com.wo.bu.dong.sample.service.ValidationService;
import com.wo.bu.dong.sample.web.ValidationController;

/**
 * 将测试范围缩小到web层（spring boot只实例化了web层，而不是整个上下文），其依赖可以通过@MockBean进行mock并注入
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ValidationController.class)
public class ValidationControllerTest {

    @Autowired
    private MockMvc           mockMvc;

    @MockBean
    private ValidationService service;

    @Test
    public void testCarIsValid() throws Exception {
        when(service.carIsValid(Matchers.any(CarReq.class))).thenAnswer(new Answer<ResultDTO<CarDTO>>() {
            @Override
            public ResultDTO<CarDTO> answer(InvocationOnMock invocation) throws Throwable {
                return new ResultDTO<>();
            }
        });
        String url = "/validation/valid";
        mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("SUCCESS")));
    }

    @Test
    public void testCarIsInvalid() throws Exception {
        when(service.carIsInvalid(Matchers.any(CarReq.class))).then(new Answer<ResultDTO<CarDTO>>() {
            @Override
            public ResultDTO<CarDTO> answer(InvocationOnMock invocation) throws Throwable {
                return ResultDTO.getResultOfParamException("参数格式不正确");
            }
        });
        String url = "/validation/invalid";
        mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("EXCEPTION")));
    }

}
