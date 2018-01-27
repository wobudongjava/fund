package com.wo.bu.dong.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wo.bu.dong.common.log.logback.LogbackWebFilter;

@Configuration
public class FilterConfig {

    @Bean
    protected FilterRegistrationBean logbackWebFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LogbackWebFilter());
        bean.addUrlPatterns("/*");
        bean.setName("logbackWebFilter");
        return bean;
    }
}
