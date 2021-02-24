package info.albertcode.utils.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class OneKeyOneValue {
    private Map<String, String> keyValue;

    public OneKeyOneValue() {
        keyValue = new HashMap<>();
    }

    public Integer addKey(String key) {
        keyValue.put(key, "");
        return keyValue.size();
    }

    public Integer addKeyValue(String key, String value) {
        keyValue.put(key, value);
        return keyValue.size();
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        Set<String> keySet = keyValue.keySet();
        Iterator<String> it =keySet.iterator();
        while (it.hasNext()){
            String key = it.next();
            String value = keyValue.get(key);
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
}
