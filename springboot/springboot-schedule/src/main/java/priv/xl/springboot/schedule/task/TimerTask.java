package priv.xl.springboot.schedule.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Timer;

/**
 * Timer类实现计划任务
 *
 * @author lei.xu
 * @since 2023/3/28 1:52 下午
 */
@Component
public class TimerTask implements InitializingBean {

    public void taskReal() {
        java.util.TimerTask timerTask = new java.util.TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer计划任务触发...");
            }
        };

        Timer schedule = new Timer();
        schedule.schedule(timerTask, 0L, 1000 * 60L);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskReal();
    }
}
