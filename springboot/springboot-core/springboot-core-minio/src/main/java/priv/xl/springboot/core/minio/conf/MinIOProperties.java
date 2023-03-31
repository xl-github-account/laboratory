package priv.xl.springboot.core.minio.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinIO配置信息
 *
 * @author lei.xu
 * @since 2023/3/30 10:39 上午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "springboot-xl.minio")
public class MinIOProperties {

    private String endpoint;

    private String username;

    private String password;

    private String accessKey;

    private String accessSecret;

}
