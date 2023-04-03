package priv.xl.springboot.iot.mqtt.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import priv.xl.springboot.iot.mqtt.channel.conf.MQTTGateway;
import priv.xl.springboot.iot.mqtt.channel.conf.MQTTProperties;

/**
 * 虚拟设备
 *
 * @author lei.xu
 * @since 2023/3/29 2:18 下午
 */
//@Component
@RequiredArgsConstructor
public class VirtualDeviceTask {

    private final MQTTGateway mqttGateway;
    private final MQTTProperties mqttProperties;

    @Scheduled(cron = "${springboot-xl.iot.virtual-device.cron:0/10 * * * * ?}")
    public void task() {
        this.device01();
    }

    public void device01() {
        String topic = this.mqttProperties.getTopics().split(",")[0];
        this.mqttGateway.sendToMqtt(topic, "test");
        System.out.println("::发送消息到MQTT::设备01::主题[" + topic + "]::消息内容[test]");
    }

}
