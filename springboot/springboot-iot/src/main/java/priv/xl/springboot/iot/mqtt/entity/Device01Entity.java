package priv.xl.springboot.iot.mqtt.entity;

import lombok.Data;

/**
 * 设备01数据表
 *
 * @author lei.xu
 * @since 2023/3/29 4:20 下午
 */
@Data
public class Device01Entity {

    /**
     * 消息发送的时间戳(TAOS表主键)
     */
    private Long ts;

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 订阅主题
     */
    private String mqttTopic;

    /**
     * qos
     */
    private Integer qos;

    /**
     * 发送的数据包
     */
    private String payload;

}
