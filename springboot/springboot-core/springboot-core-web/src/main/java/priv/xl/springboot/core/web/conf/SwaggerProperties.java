package priv.xl.springboot.core.web.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 全局参数
 *
 * @author lei.xu
 * 2023/1/13 9:33 上午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "springboot-xl.swagger")
public class SwaggerProperties {

    private String group;

    private String title;

    private String description;

    private String author;

    private String version;

    private String basePackage;

    private boolean enabled = false;

}
