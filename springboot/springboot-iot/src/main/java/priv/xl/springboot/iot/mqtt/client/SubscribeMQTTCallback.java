package priv.xl.springboot.iot.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author lei.xu
 * @since 2023/4/3 4:22 下午
 */
public class SubscribeMQTTCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("MQTT消息订阅客户端断开连接...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("============================= MQTT消息订阅客户端接收到消息 =============================");
        System.out.println("主题: " + topic);
        System.out.println("消息内容: " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
