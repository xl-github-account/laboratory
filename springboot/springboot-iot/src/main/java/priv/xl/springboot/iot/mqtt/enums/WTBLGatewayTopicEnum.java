package priv.xl.springboot.iot.mqtt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 物通博联网关主题枚举
 *
 * @author lei.xu
 * @since 2023/3/29 5:52 下午
 */
@Getter
@AllArgsConstructor
public enum WTBLGatewayTopicEnum {

    /**
     * 上行主题
     */
    Up_Topic("/up/gateway/WG583LL0722063001434"),

    /**
     * 下行主题
     */
    Down_Topic("/down/gateway/WG583LL0722063001434"),

    /**
     * 设备告警测试
     */
    Alarm_Test(" /up/gateway/alarm-test");

    private String topic;

    public boolean equalsTopic(String topic) {
        return this.topic.equals(topic);
    }

    public static WTBLGatewayTopicEnum match(String topic) {
        return Arrays.stream(values())
                .filter(e -> e.equalsTopic(topic))
                .findFirst().orElse(null);
    }

    public static String[] topics() {
        return Arrays.stream(values()).map(WTBLGatewayTopicEnum::getTopic).toArray(String[]::new);
    }

}
