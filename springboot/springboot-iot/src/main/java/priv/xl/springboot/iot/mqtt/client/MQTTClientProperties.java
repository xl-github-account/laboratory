package priv.xl.springboot.iot.mqtt.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQTT客户端配置信息
 *
 * @author lei.xu
 * @since 2023/4/3 10:27 上午
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "springboot-xl.mqtt.client")
public class MQTTClientProperties {

    private String url;

    private String clientId;

    private String username;

    private String password;

    private String timeout;

    private Integer keepalive;

    /**
     * 自动清理会话, 默认true
     */
    private Boolean cleanSession = true;

    /**
     * 自动重建连接, 默认true
     */
    private Boolean reconnect = true;

    /**
     * 消息发送QOS
     * Qos0: 消息最多发布一次
     * Qos1: 消息至少发布一次
     * Qos2: 消息发布一次
     */
    private Integer qos = 1;

}
