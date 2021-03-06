package info.albertcode.utils.pair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Description: 最简单的键值对模式，键不可重复，且一个键只有一个值
 * @Author: Albert Shen
 */

public class OneKeyOneValue {
    private Map<String, Object> keyValue;

    public OneKeyOneValue() {
        keyValue = new LinkedHashMap<>();
    }

    public OneKeyOneValue(String jsonString){
        keyValue = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        Iterator<String> iterator = jsonObject.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Object value = jsonObject.get(key);
            keyValue.put(key, value);
        }
    }

    public Iterator<String> getKeyIterator() {
        return keyValue.keySet().iterator();
    }

    /**
     * 应该接收的类型：Iterator<Map.Entry<String, Object>>
     * @return
     */
    public Iterator<Map.Entry<String, Object>> getIterator() {
        return keyValue.entrySet().iterator();
    }

    public Object getValue(String key){
        return keyValue.get(key);
    }

    /**
     * 若存在指定键值对，则不作操作，反之将值设置为空字符串插入
     * @param key
     */
    public Integer addKey(String key) {
        keyValue.putIfAbsent(key, "");
        return keyValue.size();
    }

    /**
     * 若原本存在对应键，则替换为传入的键值对，反之直接添加传入键值对
     */
    public Integer addValue(String key, Object value) {
        keyValue.put(key, value);
        return keyValue.size();
    }

    /**
     * 删除指定键及其对应的值
     */
    public Integer deleteKey(String key) {
        keyValue.remove(key);
        return keyValue.size();
    }

    /**
     * 只有当键值对均与传入参数相同时，才删除键值对
     */
    public Integer deleteValue(String key, Object value) {
        keyValue.remove(key, value);
        return keyValue.size();
    }

    public void clear() {
        keyValue.clear();
    }

    public Integer size() {
        return keyValue.size();
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        Iterator<String> iterator = keyValue.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Object value = keyValue.get(key);
            jsonObject.put(key, value);
        }
        return jsonObject.toJSONString();
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
