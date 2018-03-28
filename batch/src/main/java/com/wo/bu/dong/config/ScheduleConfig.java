package com.wo.bu.dong.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.wo.bu.dong.batch.api.ExecJobBusiness;

@Configuration
public class ScheduleConfig {

    @Autowired
    private ExecJobBusiness execJobBusiness;

    @Bean //default beanBame = methodName
    public MethodInvokingJobDetailFactoryBean testJob() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        String targetMethod = "execute";
        bean.setName("testJob");
        bean.setGroup(Scheduler.DEFAULT_GROUP);
        bean.setTargetObject(execJobBusiness);
        bean.setTargetMethod(targetMethod);
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
