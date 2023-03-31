package priv.xl.toolkit.json;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.function.Function;

/**
 * 基于Hutool的JSON阅读工具, 提供JSON消息的无损读取
 *
 * @author lei.xu
 * @since 2022/9/11 11:18 上午
 */
public final class JSONReader {

    private final JSONObject originJson;

    public JSONReader(JSONObject hutoolJson) {
        this.originJson = hutoolJson;
    }

    public JSONReader(String jsonStr) {
        this(JSONUtil.parseObj(jsonStr));
    }

    // Alibaba FastJson 支持
    public JSONReader(com.alibaba.fastjson.JSONObject fastJson) {
        this(fastJson.toString());
    }

    // Alibaba FastJson2 支持
    public JSONReader(com.alibaba.fastjson2.JSONObject fastJson2) {
        this(fastJson2.toString());
    }

    // Google Gson 支持
    public JSONReader(com.google.gson.JsonObject gson) {
        this(gson.toString());
    }

    // 其它JSON框架支持(不安全, 慎用!!!)
    public JSONReader(Object jsonObj) {
        this(jsonObj.toString());
    }

    /**
     * 获取指定key的字符串值
     *
     * @param key {@link this#getter(JSONObject, String)}
     * @return 字符串
     */
    public String getStr(final String key) {
        return this.getter(this.originJson, key);
    }

    /**
     * 在Json对象中寻址传入的key, 使用传入的处理逻辑将数据处理为指定对象返回
     *
     * @param key             {@link this#getter(JSONObject, String)}
     * @param convertFunction 转换处理函数: 入参String, 返回T
     * @param emptyFunction   空值处理函数: 入参String, 返回T
     * @param <T>             the T
     * @return convertFunction -> T
     */
    public  <T> T getStrConvertT(final String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        String str = this.getStr(key);
        return str.isEmpty() ? emptyFunction.apply(str) : convertFunction.apply(str);
    }

    public JSONObject getJson(final String key) {
        return this.getStrConvertT(key, JSONUtil::parseObj, str -> JSONUtil.createObj());
    }

    public JSONArray getJsonArr(final String key) {
        return this.getStrConvertT(key, JSONUtil::parseArray, str -> JSONUtil.createArray());
    }

    public Boolean getBool(final String key, Function<String, Boolean> emptyFunction) {
        return this.getStrConvertT(key, "true"::equals, emptyFunction);
    }

    public Boolean getBool(final String key) {
        return this.getBool(key, str -> {throw new RuntimeException("The datax script creation failed. The viewJson missing configuration.");});
    }

    public Integer getInt(final String key, Function<String, Integer> emptyFunction) {
        return this.getStrConvertT(key, str -> {
            // 校验是否是整数
            if (NumberUtil.isInteger(str)) {
                return Integer.valueOf(str);
            }
            throw new RuntimeException("The datax script creation failed. The viewJson format error.");
        }, emptyFunction);
    }

    public Integer getInt(final String key) {
        return this.getInt(key, str -> null);
    }

    public Long getLong(final String key, Function<String, Long> emptyFunction) {
        return this.getStrConvertT(key, str -> {
            // 校验是否是长整型
            if (NumberUtil.isLong(str)) {
                return Long.valueOf(str);
            }
            throw new RuntimeException("The datax script creation failed. The viewJson format error.");
        }, emptyFunction);
    }

    public Long getLong(final String key) {
        return this.getLong(key, str -> null);
    }

    public Double getDouble(final String key, Function<String, Double> emptyFunction) {
        return this.getStrConvertT(key , str -> {
            // 校验是否是整数或浮点数
            if (NumberUtil.isDouble(str) || NumberUtil.isDouble(str)) {
                return Double.valueOf(str);
            }
            throw new RuntimeException("The datax script creation failed. The viewJson format error.");
        }, emptyFunction);
    }

    public Double getDouble(final String key) {
        return this.getDouble( key, str -> null);
    }

    /**
     * 在JSON对象中寻址传入的key, 并将value转换为String返回
     * <p>
     * 需要注意的是, key如果为多级节点, 必须保证除最后一级节点外, 其它节点为JSON格式(可以为空), 方法不校验每一级节点的类型,
     * 如果中间节点为字符串格式, 而无法转为JSON, 方法会抛出JSON异常
     *
     * @param targetJson 读取目标JSON对象
     * @param key        key, 多级节点使用 . 分割, 需要获取数组值时, 可以使用key[index]的格式, 举例:
     *                   使用reader.readerColumn[1].columnName, 在viewJson中可以寻址到指定的字段名[user_info:be]
     * @return 空值时返回空字符串, 非空时根据寻址到的value而定, 如下:
     * 1. 字符串格式: 直接转换;
     * 2. JSON对象: JSON格式字符串;
     * 3. JSONArray对象: JSONArray字符串
     */
    private String getter(JSONObject targetJson, final String key) {
        if (null == key) {
            throw new RuntimeException("The datax script creation failed. The viewJson format error.");
        }

        // 如果是空字符串, 则返回root节点
        if (key.isEmpty()) {
            return targetJson.toString();
        }

        String[] kpath = key.split("\\.");
        for (final String k : kpath) {
            if (k == null || k.isEmpty()) {
                throw new RuntimeException("The datax script creation failed. The viewJson format error.");
            }
        }

        // 最终获取的value
        Object target;

        // 1. 获取当前key的value
        String thisKey = kpath[0];
        if (thisKey.contains("[") && thisKey.contains("]")) {
            // 需要获取数组指定一个下标数据的情况, 按照格式拆分当前的key: key[index]
            String[] thisKeyArr = thisKey.split("\\[");
            // 获取整个数组
            String arrJsonStr = this.getter(targetJson, thisKeyArr[0]);
            JSONArray arrJson = arrJsonStr.isEmpty() ? new JSONArray() : JSONUtil.parseArray(arrJsonStr);
            // 拆分出需要的下标, 将数组字符串转为对象并获取指定的value
            int index = Integer.parseInt(thisKeyArr[1].replace("]", ""));
            target = (arrJson.size() == 0 || arrJson.size() < index) ? "" : arrJson.get(index);
        } else {
            target = targetJson.get(thisKey);
        }

        // 2. 转换当前节点为字符串, 准备向下级节点获取数据
        String targetStr = (target instanceof JSONObject || target instanceof JSONArray) ? JSONUtil.toJsonStr(target) : ("" + target);
        if (targetStr.isEmpty() || "null".equals(targetStr) || "[]".equals(targetStr) || "{}".equals(targetStr)) {
            return "";
        }

        // 3. 依次递归寻址最终的value
        if (kpath.length > 1) {
            // 将JSON改为下一次递归使用的JSON
            targetJson = JSONUtil.parseObj(targetStr);
            // 组织后续节点的key, 以.分割的字符串
            String nextKey = kpath[1];
            for (int i = 2; i < kpath.length; i++) {
                nextKey = String.join(".", nextKey, kpath[i]);
            }
            targetStr = this.getter(targetJson, nextKey);
        }
        return targetStr;
    }

}
