package priv.xl.springboot.core.web.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Spring Mvc配置类
 *
 * @author lei.xu
 * 2023/1/13 9:46 上午
 */
@Configuration
@ComponentScan("priv.xl.springboot")
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 向JsonMessageConverts中添加自定义的GsonHttpMessageConvert
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverterExtension gsonHttpMessageConverter = new GsonHttpMessageConverterExtension();
        converters.add(gsonHttpMessageConverter);
        super.configureMessageConverters(converters);
    }

    /**
     * 开放swagger 2的静态资源
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
