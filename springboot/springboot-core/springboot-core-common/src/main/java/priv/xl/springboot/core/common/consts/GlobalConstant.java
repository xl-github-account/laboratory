package priv.xl.springboot.core.common.consts;

/**
 * 全局常量
 *
 * @author lei.xu
 * 2023/1/12 4:57 下午
 */
public interface GlobalConstant {

    /**
     * 布尔值 - int 描述
     */
    Integer BOOL_CONVERT_INT_TRUE = 1;

    Integer BOOL_CONVERT_INT_FALSE = 0;

    static boolean intBoolConvert(int bool) {
        return bool == BOOL_CONVERT_INT_TRUE;
    }

    /**
     * 布尔值 - string 描述
     */
    String BOOL_CONVERT_STRING_TRUE = "1";

    String BOOL_CONVERT_STRING_FALSE = "0";

    static boolean stringBoolConvert(String bool) {
        return BOOL_CONVERT_STRING_TRUE.equals(bool);
    }

}
