package priv.xl.springboot.schedule.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Quartz框架实现计划任务
 *
 * @author lei.xu
 * @since 2023/3/21 3:22 下午
 */
public class QuartzTask implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Quartz计划任务触发...");
    }

}
