package com.wo.bu.dong.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.common.base.ResultDTO;
import com.wo.bu.dong.sample.dto.DataDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("data")
public class DataController {

    @GetMapping("null")
    @ResponseBody
    public ResultDTO<DataDTO> sampleTypeNull() {
        log.info("sampleType==> begin");
        ResultDTO<DataDTO> result = new ResultDTO<>();
        DataDTO data = new DataDTO();
        result.setData(data);
        log.info("sampleType, result={}", result);
        log.info("sampleType==> end");
        return result;
    }

    @GetMapping("notNull")
    @ResponseBody
    public ResultDTO<DataDTO> sampleType() {
        log.info("sampleType==> begin");
        ResultDTO<DataDTO> result = new ResultDTO<>();
        DataDTO data = new DataDTO();
        data.init();
        result.setData(data);
        log.info("sampleType, result={}", result);
        log.info("sampleType==> end");
        return result;
    }
}
