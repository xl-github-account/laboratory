package priv.xl.springboot.iot.mqtt.channel.handle;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import priv.xl.springboot.iot.mqtt.enums.WTBLGatewayTopicEnum;

import java.util.Date;
import java.util.UUID;

/**
 * MQTT消息处理器
 *
 * @author lei.xu
 * @since 2023/3/29 4:27 下午
 */
public class MQTTMessageHandle implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        /*Device01Entity device = new Device01Entity();
        device.setPayload(message.getPayload().toString());

        MessageHeaders messageHeaders = message.getHeaders();
        UUID msgId = messageHeaders.getId();
        if (null != msgId) {
            device.setMsgId(msgId.toString().replaceAll("-", ""));
        }

        device.setQos(messageHeaders.get("mqtt_receivedQos", Integer.class));
        device.setMqttTopic(messageHeaders.get("mqtt_receivedTopic", String.class));
        device.setTs(messageHeaders.getTimestamp());

        SpringContextUtil.getBean(Device01Dao.class).add(device);*/

        /*MessageHeaders messageHeaders = message.getHeaders();
        String topic = messageHeaders.get("mqtt_receivedTopic", String.class);

        System.out.println("============================= 管道模式接收到MQTT消息 =============================");
        switch (WTBLGatewayTopicEnum.match(topic)) {
            case Up_Topic:
                System.out.println("上行主题");
                break;
            case Down_Topic:
                System.out.println("下行主题");
                break;
            case Alarm_Test:
                System.out.println("设备告警消息");
        }

        UUID msgId = messageHeaders.getId();
        if (null != msgId) {
            System.out.println("消息id: " + msgId.toString().replaceAll("-", ""));
        }
        Long ts = messageHeaders.getTimestamp();
        Assert.notNull(ts, "时间戳为空");
        System.out.println("消息时间戳: " + DateUtil.format(new Date(ts), DatePattern.NORM_DATETIME_MS_PATTERN));
        System.out.println("消息QOS: " + messageHeaders.get("mqtt_receivedQos", Integer.class));
        System.out.println("消息内容: " + message.getPayload());*/
    }

}
