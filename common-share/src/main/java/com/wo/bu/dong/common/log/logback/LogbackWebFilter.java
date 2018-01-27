package com.wo.bu.dong.common.log.logback;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogbackWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //设置请求唯一标识UUID
            LogBackThreadLocal.initCurrentContext();
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("==>LogbackFilter ERROR, errorMsg:{}", e.getMessage(), e);
        } finally {
            //请求处理完成-移除UUID
            LogBackThreadLocal.removeCurrentContext();
        }
    }

    @Override
    public void destroy() {
    }

}
