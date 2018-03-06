package com.wo.bu.dong.sample.web.mock;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.wo.bu.dong.sample.service.ValidationService;
import com.wo.bu.dong.sample.web.ValidationController;

@RunWith(SpringRunner.class)
@WebMvcTest(ValidationController.class)
public class ValidationControllerTest {

    @Autowired
    private MockMvc           mockMvc;

    @MockBean
    private ValidationService service;

    @Test
    public void testCarIsValid() throws Exception {
        when(service.carIsInvalid(null)).thenReturn(null);
        String url = "validation/valid";
        mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Hello")));
    }

    @Test
    public final void testCarIsInvalid() {
        fail("Not yet implemented"); // TODO
    }

}
