package priv.xl.springboot.core.database.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import priv.xl.springboot.core.common.yaml.YamlPropertySourceFactory;

/**
 * MyBatisPlus配置
 *
 * @author lei.xu
 * @since 2023/3/29 3:10 下午
 */
@Configuration
@MapperScan("priv.xl.springboot")
@PropertySource(factory = YamlPropertySourceFactory.class, value = {"classpath:springboot-core-database.yml"})
public class MyBatisPlusConfig {
}
