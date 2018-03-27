package com.wo.bu.dong.config;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.wo.bu.dong.batch.api.ExecJobBusiness;

@Configuration
public class ScheduleConfig {

    @Bean(name = "testJob")
    public MethodInvokingJobDetailFactoryBean testJob(ExecJobBusiness execJobBusiness) {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        String targetMethod = "execute";
        bean.setName("testJob");
        bean.setGroup(Scheduler.DEFAULT_GROUP);
        bean.setTargetObject(execJobBusiness);
        bean.setTargetMethod(targetMethod);
        bean.setConcurrent(false);
        return bean;
    }

    @Bean(name = "testTrigger")
    public CronTriggerFactoryBean testTrigger(MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(jobDetail.getObject());
        //run every morning at 6 AM
        bean.setCronExpression("0/3 * * * * ? *");
        return bean;
    }

    @Bean(name = "scheduler232")
    public SchedulerFactoryBean scheduler(Trigger triggers) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(triggers);
        return bean;
    }
}
