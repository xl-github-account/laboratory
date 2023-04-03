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
import priv.xl.springboot.iot.mqtt.channel.conf.MQTTGateway;
import priv.xl.toolkit.id.IDGenerator;

/**
 * Description: 测试控制器
 * Email: xulei@voxelume.com
 *
 * @author lei.xu
 * @since 2023/3/28 3:18 下午
 * © Copyright 川谷汇（北京）数字科技有限公司 Corporation All Rights Reserved.
 */
@Api(tags = "MQTT管道模式 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mqtt/channel-mode")
public class ChannelController {

    private final MQTTGateway mqttGateway;

    @ApiOperation("灯控开关")
    @PostMapping("/light-switch")
    public HttpResult lightSwitch(@ApiParam(value = "灯控开关", required = true) @RequestParam Integer lightSwitch) {
        JSONObject params = JSONUtil.createObj()
                .putOnce("cmdId", 87)
                .putOnce("gatewaySn", "WG583LL0722063001434")
                .putOnce("devSn", "light_001")
                .putOnce("varList", JSONUtil.createObj().putOnce("light_switch", lightSwitch))
                .putOnce("req", IDGenerator.uuid());
        System.out.println(params.toString());
        this.mqttGateway.sendToMqtt("/down/gateway/WG583LL0722063001434", 1, params.toString());
        return HttpResult.success();
    }

}
