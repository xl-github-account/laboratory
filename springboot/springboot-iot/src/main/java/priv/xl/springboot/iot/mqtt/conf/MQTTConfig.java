package priv.xl.springboot.iot.mqtt.conf;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.*;
import priv.xl.springboot.iot.mqtt.enums.WTBLGatewayTopicEnum;
import priv.xl.springboot.iot.mqtt.handle.MQTTMessageHandle;

/**
 * Description: MQTT配置
 * Email: xulei@voxelume.com
 *
 * @author lei.xu
 * @since 2023/3/28 3:05 下午
 * © Copyright 川谷汇（北京）数字科技有限公司 Corporation All Rights Reserved.
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MQTTProperties.class)
public class MQTTConfig {

    private final MQTTProperties mqttProperties;

    // ====================================== 客户端工厂 ======================================

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        String[] clusterUrl = this.mqttProperties.getUrl().split(",");
        connOpts.setServerURIs(clusterUrl);
        connOpts.setUserName(this.mqttProperties.getUsername());
        connOpts.setPassword(this.mqttProperties.getPassword().toCharArray());

        DefaultMqttPahoClientFactory pahoClientFactory = new DefaultMqttPahoClientFactory();
        pahoClientFactory.setConnectionOptions(connOpts);
        return pahoClientFactory;
    }

    // ====================================== 消息发布 ======================================

    /**
     * 出站管道
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * 消息发布者
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttPahoClientFactory) {
        String outboundClientId = this.mqttProperties.getClientId() + "_outbound";
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(outboundClientId, mqttPahoClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setConverter(new DefaultPahoMessageConverter());
        // 默认发送到第一个主题
        String[] inboundTopics = this.mqttProperties.getTopics().split(",");
        messageHandler.setDefaultTopic(inboundTopics[0]);
        return messageHandler;
    }


    // ====================================== 消息订阅 ======================================

    /**
     * 入站管道
     */
    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    /**
     * 消息订阅者
     */
    @Bean
    public MessageProducer mqttInbound(MqttPahoClientFactory mqttPahoClientFactory) {
//        String[] inboundTopics = this.mqttProperties.getTopics().split(",");
        String[] inboundTopics = WTBLGatewayTopicEnum.topics();
        String inboundClientId = this.mqttProperties.getClientId() + "_inbound";
        // 入站管道适配器
        MqttPahoMessageDrivenChannelAdapter drivenChannelAdapter = new MqttPahoMessageDrivenChannelAdapter(inboundClientId, mqttPahoClientFactory, inboundTopics);
        drivenChannelAdapter.setCompletionTimeout(5000);
        drivenChannelAdapter.setConverter(new DefaultPahoMessageConverter());
        // 入站投递的管道
        drivenChannelAdapter.setOutputChannel(mqttInboundChannel());
        drivenChannelAdapter.setQos(1);
        return drivenChannelAdapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler messageHandler() {
        return new MQTTMessageHandle();
    }

}
