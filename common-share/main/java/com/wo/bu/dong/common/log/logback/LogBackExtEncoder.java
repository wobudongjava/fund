package com.wo.bu.dong.common.log.logback;

import java.io.IOException;

import com.zhongan.io.xdy.comm.log.ext.LogBackThreadConverter;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogBackExtEncoder extends PatternLayoutEncoder {

    static {
        PatternLayout.defaultConverterMap.put("T", LogBackThreadConverter.class.getName());
    }

    @Override
    public void doEncode(ILoggingEvent event) throws IOException {
        super.doEncode(event);
    }

}
