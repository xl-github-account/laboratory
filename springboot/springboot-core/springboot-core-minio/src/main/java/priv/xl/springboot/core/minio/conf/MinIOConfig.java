package priv.xl.springboot.core.minio.conf;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import priv.xl.springboot.core.common.yaml.YamlPropertySourceFactory;
import priv.xl.springboot.core.minio.core.MyMinIOClient;

/**
 * Description: MinIO配置类
 * Email: xulei@voxelume.com
 *
 * @author lei.xu
 * @since 2023/3/17 8:59 上午
 * © Copyright 川谷汇（北京）数字科技有限公司 Corporation All Rights Reserved.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MinIOProperties.class)
@PropertySource(value = {"classpath:springboot-core-minio.yml"}, factory = YamlPropertySourceFactory.class)
public class MinIOConfig {

    private final MinIOProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        MinioClient.Builder builder = MinioClient.builder();
        builder.endpoint(this.minioProperties.getEndpoint());
        if (!StringUtils.isAnyEmpty(this.minioProperties.getAccessKey(), this.minioProperties.getAccessSecret())) {
            // 优先使用accessKey&Secret访问
            builder.credentials(this.minioProperties.getAccessKey(), this.minioProperties.getAccessSecret());
        } else if (!StringUtils.isAnyEmpty(this.minioProperties.getUsername(), this.minioProperties.getPassword())) {
            // 使用用户名和密码访问
            builder.credentials(this.minioProperties.getUsername(), this.minioProperties.getPassword());
        } else {
            throw new IllegalArgumentException("需要配置MinIO用户");
        }

        return builder.build();
    }

    @Bean
    public MyMinIOClient myMinioClient(MinioClient minioClient) {
        log.info("初始化MinIO Client...");
        return new MyMinIOClient(minioClient);
    }

}
