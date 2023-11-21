package priv.xl.springboot.schedule.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.util.Assert;
import priv.xl.springboot.core.common.context.SpringContextHolder;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Spring Scheduled定时任务工具
 *
 * @author lei.xu
 * @since 2023/4/13 5:05 PM
 */
public class SpringScheduledUtil {

    /**
     * 检查某个任务今天是否还会执行
     *
     * @param cron 任务的CRON表达式
     * @return bool
     */
    public static boolean willTheTaskStillBeExecutedToday(String cron) {
        // 获取下一次任务执行时间
        CronExpression cronExpression = CronExpression.parse(cron);
        LocalDateTime nextDateTime = cronExpression.next(LocalDateTime.now());
        Assert.notNull(nextDateTime, "CRON解析失败");
        // 当前时间在下一次执行时间之前, 且下次执行日期为今天, 则今天还会继续执行
        return LocalDateTime.now().isBefore(nextDateTime) && LocalDate.now().equals(nextDateTime.toLocalDate());
    }

    /**
     * 检查某个任务今天是否还会执行
     *
     * @param scheduledTaskClass 定时任务类
     * @param taskMethodName     定时任务实现方法名
     * @param taskMethodParams   方法入参
     * @return bool
     */
    public static boolean willTheTaskStillBeExecutedToday(Class<?> scheduledTaskClass, String taskMethodName, Class<?>... taskMethodParams) {
        try {
            Method method = scheduledTaskClass.getMethod(taskMethodName, taskMethodParams);
            Scheduled scheduled = method.getAnnotation(Scheduled.class);
            // 获取配置的CRON表达式
            String cron = scheduled.cron();
            if (cron.startsWith("${")) {

                String[] cronArr = cron.split(":");
                String property = cronArr[0].replace("${", "").replace("}", "");
                cron = SpringContextHolder.getProperty(property, cronArr[1]);

            }
            return willTheTaskStillBeExecutedToday(cron);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
