package priv.xl.springboot.core.web.conf;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 2 配置器
 *
 * @author lei.xu
 * 2023/1/13 9:32 上午
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@RequiredArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
public class Swagger2Configurer {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(this.swaggerProperties.isEnabled())
                .groupName(this.swaggerProperties.getGroup())
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.swaggerProperties.getTitle())
                .description(this.swaggerProperties.getDescription())
                .version(this.swaggerProperties.getVersion())
                .contact(new Contact(this.swaggerProperties.getAuthor(), "", ""))
                .build();
    }

}
