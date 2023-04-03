package priv.xl.springboot.iot.mqtt.channel.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: MQTT配置信息
 * Email: xulei@voxelume.com
 *
 * @author lei.xu
 * @since 2023/3/28 3:04 下午
 * © Copyright 川谷汇（北京）数字科技有限公司 Corporation All Rights Reserved.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "springboot-xl.mqtt")
public class MQTTProperties {

    private String url;

    private String clientId;

    private String topics;

    private String username;

    private String password;

    private String timeout;

    private String keepalive;

}
