package priv.xl.springboot.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lei.xu
 * @since 2023/3/21 3:12 下午
 */
@EnableScheduling
@SpringBootApplication
public class SpringBootScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootScheduleApplication.class, args);
    }

}
