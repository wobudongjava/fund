package com.wo.bu.dong.common.log.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class TPlaceholderConverter extends ClassicConverter {

    /**
     * 根据当前线程号获取UUID
     * 
     * @param loggingEvent
     * @return
     */
    @Override
    public String convert(ILoggingEvent loggingEvent) {

        return LogBackThreadLocal.getCurrentContext();
    }
}
