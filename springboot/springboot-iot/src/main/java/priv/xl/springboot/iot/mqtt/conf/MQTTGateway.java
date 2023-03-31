package priv.xl.springboot.iot.mqtt.conf;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * MQTT网关
 *
 * @author lei.xu
 * @since 2023/3/29 4:03 下午
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MQTTGateway {

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);

}
