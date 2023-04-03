package priv.xl.springboot.iot.mqtt.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息订阅MQTT客户端
 *
 * @author lei.xu
 * @since 2023/4/3 4:18 下午
 */
@Slf4j
@Component
public class SubscribeMQTTClient {

    private final MQTTClientProperties mqttClientProperties;

    /**
     * 单例的MQTT客户端
     */
    private static volatile MqttClient singleton;

    @Autowired
    public SubscribeMQTTClient(MQTTClientProperties mqttClientProperties) {
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
                        singleton.setCallback(new SubscribeMQTTCallback());
                        log.info("客户端模式 - MQTT消息订阅客户端初始化...");
                        // 建立连接
                        this.connect();
                        log.info("客户端模式 - MQTT消息订阅客户端已建立连接...");
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
     * 订阅某个主题
     *
     * @param topic 主题名称
     */
    public void subscribe(String topic) {
        try {
            this.connect();
            singleton.subscribe(topic);
            log.info("MQTT客户端订阅主题[{}]成功", topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消订阅主题
     *
     * @param topic 主题名称
     */
    public void unsubscribe(String topic) {
        try {
            this.connect();
            singleton.unsubscribe(topic);
            log.info("MQTT客户端取消订阅主题[{}]成功", topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
