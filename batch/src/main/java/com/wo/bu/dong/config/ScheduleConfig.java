package com.wo.bu.dong.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.wo.bu.dong.batch.api.ExecJobTask;

@Configuration
public class ScheduleConfig {

    @Autowired
    private ExecJobTask     execJobBusiness;

    private static final String TARGET_METHOD = "execute";
    private static final String DEFAULT_GROUP = Scheduler.DEFAULT_GROUP;

    @Bean //default beanBame = methodName
    public MethodInvokingJobDetailFactoryBean testJob() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setName("testJob");
        bean.setGroup(DEFAULT_GROUP);
        bean.setTargetObject(execJobBusiness);
        bean.setTargetMethod(TARGET_METHOD);
        bean.setConcurrent(false);
        return bean;
    }

    @Bean
    public CronTriggerFactoryBean testTrigger() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(testJob().getObject());
        //run every 3 seconds
        bean.setCronExpression("0/3 * * * * ? *");
        return bean;
    }

    @Bean
    public SchedulerFactoryBean scheduler() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(testTrigger().getObject());
        return bean;
    }
}
