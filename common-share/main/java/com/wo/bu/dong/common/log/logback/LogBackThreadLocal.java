package com.wo.bu.dong.common.log.logback;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogBackThreadLocal {
    private static final ThreadLocal<String> THREAD_LOCAL_CONTEXT = new ThreadLocal<String>();

    public static String getCurrentContext() {
        try {
            String context = THREAD_LOCAL_CONTEXT.get();
            if (StringUtils.isBlank(context)) {
                context = UUID.randomUUID().toString().replace("-", "");
                THREAD_LOCAL_CONTEXT.set(context);
            }
            return context;
        } catch (Exception e) {
            log.error("==>getCurrentContext ERROR, errorMsg:{}", e.getMessage(), e);
            return UUID.randomUUID().toString();
        }
    }

    public static void initCurrentContext() {
        try {
            if (StringUtils.isNotBlank(THREAD_LOCAL_CONTEXT.get())) {
                removeCurrentContext();
            }
            String context = UUID.randomUUID().toString().replace("-", "");
            THREAD_LOCAL_CONTEXT.set(context);
        } catch (Exception e) {
            log.error("==>initCurrentContext ERROR, errorMsg:{}", e.getMessage(), e);
        }
    }

    public static void removeCurrentContext() {
        String context = THREAD_LOCAL_CONTEXT.get();
        try {
            if (StringUtils.isNotBlank(context)) {
                THREAD_LOCAL_CONTEXT.remove();
            }
        } catch (Exception e) {
            log.error("==>removeCurrentContext ERROR, errorMsg:{}", e.getMessage(), e);
        }
    }
}
