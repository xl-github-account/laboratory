package priv.xl.springboot.core.minio.utils;

import io.minio.messages.Tags;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签类扩展
 *
 * @author lei.xu
 * 2022/10/24 5:04 下午
 */
public class TagsExtend {

    private final Map<String, String> tagsMap;

    public TagsExtend() {
        this.tagsMap = new HashMap<>();
    }

    public Tags getTags() {
        return Tags.newBucketTags(this.tagsMap);
    }

    public TagsExtend put(String key, String value) {
        this.tagsMap.put(key, value);
        return this;
    }

    public TagsExtend putAll(Map<String, String> putMap) {
        this.tagsMap.putAll(putMap);
        return this;
    }

    public void delete(String key) {
        this.tagsMap.remove(key);
    }

}
