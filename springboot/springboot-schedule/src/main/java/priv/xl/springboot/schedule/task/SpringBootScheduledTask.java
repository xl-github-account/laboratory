package priv.xl.springboot.schedule.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * SpringBoot Scheduled注解实现计划任务
 *
 * @author lei.xu
 * @since 2023/3/21 3:13 下午
 */
@Component
public class SpringBootScheduledTask {

    @Scheduled(cron = "0,10 * * * * ?")
    private void trigger() {
        System.out.println("SpringBoot Scheduled计划任务触发...");
    }

}
