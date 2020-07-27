package cn.linbin.worklog.config;

import cn.linbin.worklog.task.TestJob;
import cn.linbin.worklog.task.WorkHourJob;
import cn.linbin.worklog.utils.SpringUtil;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {

    //-----------------------------------第一种执行方法
    // 配置触发器
    @Bean(name = "workHourTrigger")
    public SimpleTriggerFactoryBean workHourTrigger(JobDetail workHourJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(workHourJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        // 每5秒执行一次
        trigger.setRepeatInterval(50000000);
        return trigger;
    }

    // 配置定时任务
    @Bean(name = "workHourJobDetail")
    public MethodInvokingJobDetailFactoryBean workHourJobDetail() {
        WorkHourJob workHourJob = SpringUtil.getBean(WorkHourJob.class);
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(workHourJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("executeWorkHourJob");
        return jobDetail;
    }
    //-----------------------------------第一种执行方法



    //-----------------------------------第二种执行方法
    @Bean(name = "testJobTrigger")
    public CronTriggerFactoryBean testJobTrigger(JobDetail testJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(testJobDetail);
        // 七子表达式
        trigger.setCronExpression("0 0 22 * * ?");
        return trigger;
    }

    // 配置定时任务2
    @Bean(name = "testJobDetail")
    public MethodInvokingJobDetailFactoryBean testJobDetail() {
        TestJob testJob = SpringUtil.getBean(TestJob.class);
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(testJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("excuteTextJob");
        return jobDetail;
    }
    //-----------------------------------第二种执行方法

    // 配置注册中心
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger workHourTrigger, Trigger testJobTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(workHourTrigger, testJobTrigger);
        return bean;
    }
}
