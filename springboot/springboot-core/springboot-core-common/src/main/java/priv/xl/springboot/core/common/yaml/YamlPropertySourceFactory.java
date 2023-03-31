package priv.xl.springboot.core.common.yaml;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * 自定义Yaml文件配置读取器
 *
 * @author lei.xu
 * @since 2023/3/30 10:01 上午
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlPropertySourceFactory.class);

    @NonNull
    @Override
    public PropertySource<?> createPropertySource(String name, @NonNull EncodedResource resource) throws IOException {
        try {
            // 创建yaml文件解析工厂
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            // 设置资源内容
            yamlFactory.setResources(resource.getResource());
            // 解析成properties文件
            Properties properties = Objects.requireNonNull(yamlFactory.getObject(), "自定义Yaml获取失败");
            // 获取文件名称
            String filename = Objects.requireNonNull(resource.getResource().getFilename(), "自定义Yaml文件名获取为空");
            return StringUtils.isBlank(name) ? new PropertiesPropertySource(filename, properties) : new PropertiesPropertySource(name, properties);
        } catch (Exception e) {
            LOGGER.error("自定义Yaml解析异常, 文件名称: {}, 堆栈信息: {}", name, ExceptionUtils.getStackTrace(e));
            throw new FileNotFoundException("自定义Yaml解析异常");
        }
    }

}
