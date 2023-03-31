package priv.xl.springboot.core.common.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Spring上下文对象持有者
 *
 * @author lei.xu
 * @since 2023/3/30 10:18 上午
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringContextHolder implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (null == SpringContextHolder.applicationContext) {
            SpringContextHolder.applicationContext = applicationContext;
            LOGGER.info("项目初始化, 获取Spring上下文对象成功...");
        }
    }

    public static Object getBean(String beanName) {
        return SpringContextHolder.applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return SpringContextHolder.applicationContext.getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return SpringContextHolder.applicationContext.getBean(beanClass, beanClass);
    }

    public static boolean containsBean(String beanName) throws NoSuchBeanDefinitionException {
        return SpringContextHolder.applicationContext.containsBean(beanName);
    }

    public static boolean isSingleton(String beanName) throws NoSuchBeanDefinitionException {
        return SpringContextHolder.applicationContext.isSingleton(beanName);
    }

    public static Class<?> getType(String beanName) {
        return SpringContextHolder.applicationContext.getType(beanName);
    }

    public static String[] getAlias(String beanName) {
        return SpringContextHolder.applicationContext.getAliases(beanName);
    }

    public static Environment getEnv() {
        return SpringContextHolder.applicationContext.getEnvironment();
    }

    public static String getProperty(String key) {
        return SpringContextHolder.getEnv().getProperty(key);
    }

    public static String getProperty(String key, String defaultVal) {
        return SpringContextHolder.getEnv().getProperty(key, defaultVal);
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        return SpringContextHolder.getEnv().getProperty(key, targetType);
    }

}
