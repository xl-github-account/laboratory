package priv.xl.springboot.iot.mqtt.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 消息发布的MQTT客户端
 *
 * @author lei.xu
 * @since 2023/4/3 4:15 下午
 */
@Slf4j
@Component
public class PublishMQTTClient {

    private final MQTTClientProperties mqttClientProperties;

    /**
     * 单例的MQTT客户端
     */
    private static volatile MqttClient singleton;

    @Autowired
    public PublishMQTTClient(MQTTClientProperties mqttClientProperties) {
        this.mqttClientProperties = mqttClientProperties;
        // 建立单例的客户端对象
        if (null == singleton) {
            synchronized (PublishMQTTClient.class) {
                if (null == singleton) {
                    try {
                        // 初始化MQTT客户端
                        singleton = new MqttClient(
                                this.mqttClientProperties.getUrl(),
                                this.mqttClientProperties.getClientId() + "-" + System.currentTimeMillis(),
                                new MemoryPersistence()
                        );
                        singleton.setCallback(new PublishMQTTCallback());
                        log.info("客户端模式 - MQTT发布客户端初始化...");
                        // 建立连接
                        this.connect();
                        log.info("客户端模式 - MQTT发布客户端已建立连接...");
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 客户端重建连接
     */
    public void reconnect() {
        try {
            // 设置连接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(this.mqttClientProperties.getUsername());
            connOpts.setPassword(this.mqttClientProperties.getPassword().toCharArray());
            connOpts.setKeepAliveInterval(this.mqttClientProperties.getKeepalive());
            connOpts.setAutomaticReconnect(false);
            connOpts.setCleanSession(this.mqttClientProperties.getCleanSession());

            // 建立连接, 三次重试机会
            if (singleton.isConnected()) {
                singleton.disconnect();
            }
            for (int i = 0; i < 3; i++) {
                if (!singleton.isConnected()) {
                    singleton.connect(connOpts);
                }
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端建立连接, 已建立连接时不会尝试重新建立
     */
    public void connect() {
        if (singleton.isConnected()) {
            return;
        }
        reconnect();
    }

    /**
     * 发送消息到指定的topic, 该方法在执行失败后会重试3次, 间隔时间每次为1s
     *
     * @param topic     消息主题
     * @param message   消息内容
     * @param retained  是否在消息引擎中保留发布的消息
     */
    @Retryable(value = MqttException.class, backoff = @Backoff(multiplier = 1))
    public void publish(String topic, String message, boolean retained) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(this.mqttClientProperties.getQos());
        mqttMessage.setPayload(message.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setRetained(retained);

        try {
            this.connect();
            singleton.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
