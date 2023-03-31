package priv.xl.springboot.schedule.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import priv.xl.springboot.core.common.context.SpringContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.locks.LockSupport;

/**
 * 多线程实现计划任务
 *
 * @author lei.xu
 * @since 2023/3/21 3:35 下午
 */
@Component
public class ThreadRealTask implements InitializingBean, DisposableBean {

    /**
     * 触发CRON
     */
    @Value("${springboot-xl.schedule.thread-real.cron}")
    private String cron;

    /**
     * 是否在项目启动时触发
     */
    @Value("${springboot-xl.schedule.thread-real.springboot-start-trigger:true}")
    private boolean isStartTrigger;

    /**
     * 触发线程(任务执行线程)
     */
    private Thread triggerThread;

    /**
     * 触发线程停止标识, 停止后, 线程将不再执行任务
     */
    private volatile boolean triggerThreadStop = false;

    // ====================================== 任务[启动/停止/轮询/强制执行]实现 ======================================

    /**
     * 任务启动方法
     */
    public void start() {
        this.triggerThread = new Thread(() -> {

            if (!this.isStartTrigger) {
                this.sleep();
            }

            while (!triggerThreadStop) {
                this.taskReal();
                this.sleep();
            }

        });

        // 声明为守护线程后启动
        this.triggerThread.setDaemon(true);
        this.triggerThread.setName("thread-real-task");
        this.triggerThread.start();
    }

    /**
     * 任务停止方法
     */
    public void stop() {
        this.triggerThreadStop = true;

        if (this.triggerThread.getState() != Thread.State.TERMINATED) {
            this.triggerThread.interrupt();
            try {
                this.triggerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 手动执行一次任务
     */
    public static void forceTrigger() {
        System.out.println("TreadReal计划任务, 手动触发...");
        SpringContextHolder.getBean(ThreadRealTask.class).taskReal();
    }

    /**
     * 任务休眠方法
     */
    private void sleep() {
        // 解析配置的CRON表达式, 获取下一次执行日期, 核算为毫秒值
        CronExpression expression = CronExpression.parse(this.cron);
        LocalDateTime nextDateTime = expression.next(LocalDateTime.now());
        Assert.notNull(nextDateTime, "ThreadReal计划任务, 尝试休眠时, 计算下一次触发时间发生错误...");
        long nextTimestamp = nextDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        LockSupport.parkUntil(nextTimestamp);
    }

    // ====================================== 任务[启动/停止]控制 ======================================

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CronExpression.isValidExpression(this.cron)) {
            throw new IllegalArgumentException("CRON语法错误, 请检查");
        }
        this.start();
        System.out.println("ThreadReal计划任务启动...");
    }

    @Override
    public void destroy() throws Exception {
        this.stop();
        System.out.println("ThreadReal计划任务终止...");
    }

    // ====================================== 任务实现 ======================================

    public void taskReal() {
        System.out.println("TreadReal计划任务触发...");
    }

}
