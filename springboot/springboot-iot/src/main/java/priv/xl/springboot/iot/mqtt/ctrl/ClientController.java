package priv.xl.springboot.iot.mqtt.ctrl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.xl.springboot.core.web.http.HttpResult;
import priv.xl.springboot.iot.mqtt.client.PublishMQTTClient;
import priv.xl.springboot.iot.mqtt.client.SubscribeMQTTClient;
import priv.xl.toolkit.id.IDGenerator;

/**
 * @author lei.xu
 * @since 2023/4/3 1:35 下午
 */
@Api(tags = "MQTT客户端模式 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mqtt/client-mode")
public class ClientController {

    private final PublishMQTTClient publishMQTTClient;
    private final SubscribeMQTTClient subscribeMQTTClient;

    @ApiOperation("订阅主题")
    @PostMapping("/topic/subscribe")
    public HttpResult subscribe(@ApiParam(value = "主题名称", required = true) @RequestParam String topic) {
        this.subscribeMQTTClient.subscribe(topic);
        return HttpResult.success("主题[" + topic + "]订阅成功!", null);
    }

    @ApiOperation("灯控开关")
    @PostMapping("/light-switch")
    public HttpResult lightSwitch(@ApiParam(value = "灯控开关", required = true) @RequestParam Integer lightSwitch,
                                  @ApiParam(value = "订阅的主题名称", required = true) @RequestParam String topic) {
        JSONObject params = JSONUtil.createObj()
                .putOnce("cmdId", 87)
                .putOnce("gatewaySn", "WG583LL0722063001434")
                .putOnce("devSn", "light_001")
                .putOnce("varList", JSONUtil.createObj().putOnce("light_switch", lightSwitch))
                .putOnce("req", IDGenerator.uuid());
        this.publishMQTTClient.publish(topic, params.toString(), true);
        return HttpResult.success();
    }

}
