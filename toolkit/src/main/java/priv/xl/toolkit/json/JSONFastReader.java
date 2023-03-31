package priv.xl.toolkit.json;

import java.util.List;
import java.util.function.Function;

/**
 * JSON快速读取工具
 *
 * @author lei.xu
 * @since 2023/9/11 2:43 下午
 */
public class JSONFastReader {

    // ==================================== 字符串值的读取 ====================================

    public static String getStr(String jsonStr, String key) {
        return new JSONReader(jsonStr).getStr(key);
    }

    public static String getStr(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getStr(key);
    }

    public static String getStr(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return new JSONReader(fastJson).getStr(key);
    }

    public static String getStr(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return new JSONReader(fastJson2).getStr(key);
    }

    public static String getStr(com.google.gson.JsonObject gson, String key) {
        return new JSONReader(gson).getStr(key);
    }

    public static String getStr(Object jsonObj, String key) {
        return new JSONReader(jsonObj).getStr(key);
    }

    // ==================================== 读取值转换为指定对象 ====================================

    public static <T> T getStrConvertT(String jsonStr, String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        return new JSONReader(jsonStr).getStrConvertT(key, convertFunction, emptyFunction);
    }

    public static <T> T getStrConvertT(cn.hutool.json.JSONObject hutoolJson, String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        return new JSONReader(hutoolJson).getStrConvertT(key, convertFunction, emptyFunction);
    }

    public static <T> T getStrConvertT(com.alibaba.fastjson.JSONObject fastJson, String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        return new JSONReader(fastJson).getStrConvertT(key, convertFunction, emptyFunction);
    }

    public static <T> T getStrConvertT(com.alibaba.fastjson2.JSONObject fastJson2, String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        return new JSONReader(fastJson2).getStrConvertT(key, convertFunction, emptyFunction);
    }

    public static <T> T getStrConvertT(com.google.gson.JsonObject gson, String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        return new JSONReader(gson).getStrConvertT(key, convertFunction, emptyFunction);
    }

    public static <T> T getStrConvertT(Object jsonObj, String key, Function<String, T> convertFunction, Function<String, T> emptyFunction) {
        return new JSONReader(jsonObj).getStrConvertT(key, convertFunction, emptyFunction);
    }

    // ==================================== 布尔值的读取 ====================================

    public static Boolean getBool(String jsonStr, String key) {
        return new JSONReader(jsonStr).getBool(key, v -> null);
    }

    public static Boolean getBool(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getBool(key, v -> null);
    }

    public static Boolean getBool(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return new JSONReader(fastJson).getBool(key, v -> null);
    }

    public static Boolean getBool(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return new JSONReader(fastJson2).getBool(key, v -> null);
    }

    public static Boolean getBool(com.google.gson.JsonObject gson, String key) {
        return new JSONReader(gson).getBool(key, v -> null);
    }

    public static Boolean getBool(Object jsonObj, String key) {
        return new JSONReader(jsonObj).getBool(key, v -> null);
    }

    // ==================================== int值的读取 ====================================

    public static Integer getInt(String jsonStr, String key) {
        return new JSONReader(jsonStr).getInt(key);
    }

    public static Integer getInt(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getInt(key);
    }

    public static Integer getInt(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return new JSONReader(fastJson).getInt(key);
    }

    public static Integer getInt(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return new JSONReader(fastJson2).getInt(key);
    }

    public static Integer getInt(com.google.gson.JsonObject gson, String key) {
        return new JSONReader(gson).getInt(key);
    }

    public static Integer getInt(Object jsonObj, String key) {
        return new JSONReader(jsonObj).getInt(key);
    }

    // ==================================== Long值的读取 ====================================

    public static Long getLong(String jsonStr, String key) {
        return new JSONReader(jsonStr).getLong(key);
    }

    public static Long getLong(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getLong(key);
    }

    public static Long getLong(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return new JSONReader(fastJson).getLong(key);
    }

    public static Long getLong(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return new JSONReader(fastJson2).getLong(key);
    }

    public static Long getLong(com.google.gson.JsonObject gson, String key) {
        return new JSONReader(gson).getLong(key);
    }

    public static Long getLong(Object jsonObj, String key) {
        return new JSONReader(jsonObj).getLong(key);
    }

    // ==================================== 浮点值的读取 ====================================

    public static Double getDouble(String jsonStr, String key) {
        return new JSONReader(jsonStr).getDouble(key);
    }

    public static Double getDouble(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getDouble(key);
    }

    public static Double getDouble(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return new JSONReader(fastJson).getDouble(key);
    }

    public static Double getDouble(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return new JSONReader(fastJson2).getDouble(key);
    }

    public static Double getDouble(com.google.gson.JsonObject gson, String key) {
        return new JSONReader(gson).getDouble(key);
    }

    public static Double getDouble(Object jsonObj, String key) {
        return new JSONReader(jsonObj).getDouble(key);
    }

    // ==================================== JSON对象的读取 ====================================

    public static cn.hutool.json.JSONObject getJson(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getJson(key);
    }

    public static com.alibaba.fastjson.JSONObject getJson(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return com.alibaba.fastjson.JSON.parseObject(getStr(fastJson, key));
    }

    public static com.alibaba.fastjson2.JSONObject getJson(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return com.alibaba.fastjson2.JSON.parseObject(getStr(fastJson2, key));
    }

    public static com.google.gson.JsonObject getJson(com.google.gson.JsonObject gson, String key) {
        return com.google.gson.JsonParser.parseString(getStr(gson, key)).getAsJsonObject();
    }

    // ==================================== 数组读取 ====================================

    public static List<?> getArr(cn.hutool.json.JSONObject hutoolJson, String key) {
        return new JSONReader(hutoolJson).getJsonArr(key);
    }

    public static List<?> getArr(com.alibaba.fastjson.JSONObject fastJson, String key) {
        return new JSONReader(fastJson).getJsonArr(key);
    }

    public static List<?> getArr(com.alibaba.fastjson2.JSONObject fastJson2, String key) {
        return new JSONReader(fastJson2).getJsonArr(key);
    }

    public static List<?> getArr(com.google.gson.JsonObject gson, String key) {
        return new JSONReader(gson).getJsonArr(key);
    }

}
