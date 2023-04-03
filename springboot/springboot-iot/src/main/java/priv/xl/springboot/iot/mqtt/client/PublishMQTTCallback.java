package priv.xl.springboot.iot.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author lei.xu
 * @since 2023/4/3 4:22 下午
 */
public class PublishMQTTCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("MQTT消息发布客户端断开连接...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("MQTT消息发布成功, 主题: " + token.getTopics()[0]);
    }

}
