package priv.xl.springboot.core.database.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import priv.xl.springboot.core.common.consts.GlobalConstant;

import java.util.Date;

/**
 * 实体类Base
 *
 * @author lei.xu
 * @since 2023/3/29 2:38 下午
 */
@Data
public class BaseEntity {

    /**
     * 逻辑删除标识
     */
    @TableLogic(value = GlobalConstant.BOOL_CONVERT_STRING_FALSE, delval = GlobalConstant.BOOL_CONVERT_STRING_TRUE)
    protected Integer delFlag;

    /**
     * 创建人ID
     */
    protected String createBy;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 最后更新人ID
     */
    protected String lastUpdateBy;

    /**
     * 最后更新时间
     */
    protected Date lastUpdateTime;

}
