package info.albertcode.utils.pair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Description: 一个键可对应多个值
 * 具有两种解析视图，
 * 视图一：以 键（key） 分组，同一key的元素储存于同一数组中，如
 * {
 *   "name": [
 *     "Albert",
 *     "Shen",
 *     "Code",
 *     "666"
 *   ],
 *   "sex": [
 *     "male",
 *     "female",
 *     "男"
 *   ],
 *   "age": [
 *     1,
 *     2
 *   ]
 * }
 * 视图二：以 组（Group）分组，同一key的元素不可处于同一数组中，且同一Key的元素均尽量存储于前面的数组
 * [
 *   {
 *     "name": "Albert",
 *     "sex": "male",
 *     "age": 1
 *   },
 *   {
 *     "name": "Shen",
 *     "sex": "femal",
 *     "age": 2
 *   },
 *   {
 *     "name": "Code",
 *     "sex": "男"
 *   },
 *   {
 *     "name": "666"
 *   }
 * ]
 * @Author: Albert Shen
 */

public class OneKeyManyValues {
    private Map<String, List<Object>> keyValues;

    public OneKeyManyValues() {
        keyValues = new LinkedHashMap<>();
    }

    public OneKeyManyValues(String jsonString) {
        keyValues = new LinkedHashMap<>();
        JSONArray jsonArray = JSON.parseArray(jsonString);
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                this.addValue(entry.getKey(), entry.getValue());
            }
        }
    }

    public Iterator<String> getKeyIterator() {
        return keyValues.keySet().iterator();
    }

    public Iterator<Map.Entry<String, List<Object>>> getIteratorByKey() {
        return keyValues.entrySet().iterator();
    }

    public Iterator<LinkedHashMap<String, Object>> getIteratorByGroup() {
        LinkedList<LinkedHashMap<String, Object>> list = new LinkedList<>();
        Iterator<Map.Entry<String, List<Object>>> iterator = this.getIteratorByKey();
        while (iterator.hasNext()){
            Map.Entry<String, List<Object>> entry = iterator.next();
            for (int i = 0; i < entry.getValue().size(); i++){
                if (list.size() <= i){
                    LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                    map.put(entry.getKey(), entry.getValue().get(i));
                    list.add(map);
                } else {
                    list.get(i).put(entry.getKey(), entry.getValue().get(i));
                }
            }
        }
        return list.iterator();
    }

    public void addKey(String key) {
        if (!keyValues.containsKey(key)){
            keyValues.put(key, new LinkedList<>());
        }
    }

    public void addValue(String key, Object value) {
        addKey(key);
        keyValues.get(key).add(value);
    }

    public void deleteKey(String key) {
        keyValues.remove(key);
    }

    public void deleteValue(String key, Object value) {
        List<Object> objects = keyValues.get(key);
        if (objects != null){
            objects.remove(value);
        }
    }

    public void clear() {
        keyValues.clear();
    }

    public String toJsonStringByKey() {
        Iterator<Map.Entry<String, List<Object>>> iterator = this.getIteratorByKey();
        JSONArray jsonArray = new JSONArray();
        while (iterator.hasNext()){
            Map.Entry<String, List<Object>> entry = iterator.next();
            JSONArray singleArray = new JSONArray();
            for (int i = 0; i < entry.getValue().size(); i++){
                singleArray.add(entry.getValue().get(i));
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(entry.getKey(), singleArray);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 一个key可能对应多个value，此种转换方式，将整体分为多个数组，
     * 数组数量与maxNumberOfValues相同，一个数组中，不会存在相同的key，
     * 且key会尽量靠前，如：
     * @return
     */
    public String toJsonStringByGroup() {
        JSONArray jsonArray = new JSONArray();
        Iterator<Map.Entry<String, List<Object>>> iterator = this.getIteratorByKey();
        while (iterator.hasNext()){
            Map.Entry<String, List<Object>> entry = iterator.next();
            for (int i = 0; i < entry.getValue().size(); i++){
                if (jsonArray.size() <= i){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(entry.getKey(), entry.getValue().get(i));
                    jsonArray.add(jsonObject);
                } else {
                    jsonArray.getJSONObject(i).put(entry.getKey(), entry.getValue().get(i));
                }
            }
        }
        return jsonArray.toJSONString();
    }

    @Override
    public String toString() {
        return this.toJsonStringByGroup();
    }
}
