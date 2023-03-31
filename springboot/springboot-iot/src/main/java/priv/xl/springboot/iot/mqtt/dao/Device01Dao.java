package priv.xl.springboot.iot.mqtt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import priv.xl.springboot.iot.mqtt.entity.Device01Entity;

/**
 *
 * @author lei.xu
 * @since 2023/3/29 4:25 下午
 */
@Repository
public interface Device01Dao extends BaseMapper<Device01Entity> {

    @Insert("insert into iot_mqtt.device_01 (ts, msg_id, mqtt_topic, qos, payload) " +
            "values(#{device.ts}, #{device.msgId}, #{device.mqttTopic}, #{device.qos}, #{device.payload})")
    int add(@Param("device") Device01Entity device);

}
