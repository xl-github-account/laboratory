package priv.xl.springboot.core.database.conf;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import priv.xl.springboot.core.common.consts.GlobalConstant;

/**
 * MyBatisPlus框架处理器
 *
 * @author lei.xu
 * @since 2023/3/29 2:47 下午
 */
@Component
public class MybatisPlusHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("delFlag", GlobalConstant.BOOL_CONVERT_INT_FALSE, metaObject);
        this.setFieldValByName("createBy", "-1", metaObject);
        this.setFieldValByName("createTime", DateUtil.now(), metaObject);
        this.setFieldValByName("lastUpdateBy", "-1", metaObject);
        this.setFieldValByName("lastUpdateTime", DateUtil.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("lastUpdateBy", "-1", metaObject);
        this.setFieldValByName("lastUpdateTime", DateUtil.now(), metaObject);
    }

}
