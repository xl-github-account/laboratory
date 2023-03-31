package priv.xl.springboot.schedule.conf;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.xl.springboot.schedule.task.QuartzTask;

/**
 * Quartz任务触发器配置
 *
 * @author lei.xu
 * @since 2023/3/21 3:23 下午
 */
@Configuration
public class QuartzTriggerConfig {

    /**
     * 任务Job信息
     */
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(QuartzTask.class).storeDurably().build();
    }

    /**
     * 简单调度器实现
     */
    @Bean
    public Trigger triggerForTaskOne() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                // 每分钟执行一次
                .withIntervalInMinutes(1)
                // 永久执行下去
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .withIdentity("quartz task 001", "quartz task")
                .withSchedule(scheduleBuilder)
                .forJob(jobDetail())
                .build();
    }

    /**
     * CRON调度器实现
     */
    @Bean
    public Trigger triggerForTaskTwo() {
        return TriggerBuilder.newTrigger()
                .withIdentity("quartz task 002", "quartz task")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .forJob(jobDetail())
                .build();
    }

}
