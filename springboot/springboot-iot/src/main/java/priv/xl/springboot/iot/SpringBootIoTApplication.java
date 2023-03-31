package priv.xl.springboot.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description:
 * Email: xulei@voxelume.com
 *
 * @author lei.xu
 * @since 2023/3/28 3:03 下午
 * © Copyright 川谷汇（北京）数字科技有限公司 Corporation All Rights Reserved.
 */
@EnableScheduling
@SpringBootApplication
public class SpringBootIoTApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIoTApplication.class, args);
    }

}
