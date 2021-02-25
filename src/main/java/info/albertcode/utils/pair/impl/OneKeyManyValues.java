package info.albertcode.utils.pair.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import info.albertcode.utils.pair.IKeyValue;

import java.util.*;

/**
 * @Description: 一个键可对应多个值
 * @Author: Albert Shen
 */

public class OneKeyManyValues implements IKeyValue {
    private List<String> keys;
    private List<List<Object>> valuesArray;
    private Integer numberOfKeys;
    private Integer maxNumberOfValues;

    public OneKeyManyValues() {
        keys = new LinkedList<>();
        valuesArray = new LinkedList<>();
        numberOfKeys = 0;
        maxNumberOfValues = 0;
    }

    public OneKeyManyValues(String jsonString) {
        keys = new LinkedList<>();
        valuesArray = new LinkedList<>();
        numberOfKeys = 0;
        maxNumberOfValues = 0;

        JSONArray jsonArray = JSON.parseArray(jsonString);
        for (Integer i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Iterator<String> iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                Object value = jsonObject.get(key);
                this.addValue(key, value);
            }
        }
    }

    /**
     * 获取下标，此下标对应于一个键值对，此键值对的键与 传入参数key 相同
     * 当不存在此键值对时，返回-1
     */
    private Integer findKeySubscript(String key){
        for (Integer i = 0; i < keys.size(); i++){
            if (key.equals(keys.get(i))){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Set<String> getAllKeys() {
        return new HashSet<>(keys);
    }

    @Override
    public Integer addKey(String key) {
        if (findKeySubscript(key) == -1){
            this.keys.add(key);
            List<Object> values = new LinkedList<>();
            valuesArray.add(values);
            numberOfKeys += 1;
        }
        return numberOfKeys;
    }

    public void addValue(int subscript, Object value){
        valuesArray.get(subscript).add(value);
        Integer numberOfValues = valuesArray.get(subscript).size();
        if (numberOfValues > maxNumberOfValues){
            maxNumberOfValues = numberOfValues;
        }
    }

    @Override
    public Integer addValue(String key, Object value) {
        Integer subscript = findKeySubscript(key);
        if (subscript == -1){
            subscript = addKey(key) - 1;
        }
        addValue(subscript, value);
        return numberOfKeys;
    }

    private void deleteKey(int subscript){
        keys.remove(subscript);
        valuesArray.remove(subscript);
        numberOfKeys -= 1;
        maxNumberOfValues = 0;
        for (int i = 0; i < numberOfKeys; i++){
            if (valuesArray.get(i).size() > maxNumberOfValues){
                maxNumberOfValues = valuesArray.get(i).size();
            }
        }
    }

    @Override
    public Integer deleteKey(String key) {
        Integer subscript = findKeySubscript(key);
        if (subscript != -1){
            deleteKey(subscript);
        }
        return numberOfKeys;
    }

    @Override
    public Integer deleteValue(String key, Object value) {
        Integer keySubscript = findKeySubscript(key);
        if (keySubscript != -1){
            List<Object> values = valuesArray.get(keySubscript);
            values.remove(values);
            maxNumberOfValues = 0;
            for (int i = 0; i < numberOfKeys; i++){
                if (valuesArray.get(i).size() > maxNumberOfValues){
                    maxNumberOfValues = valuesArray.get(i).size();
                }
            }
        }
        return numberOfKeys;
    }

    @Override
    public void clear() {
        keys.clear();
        valuesArray.clear();
        numberOfKeys = 0;
        maxNumberOfValues = 0;
    }

    @Override
    public Integer size() {
        return numberOfKeys;
    }

    /**
     * 一个key可能对应多个value，此种转换方式，将整体分为多个数组，
     * 数组数量与maxNumberOfValues相同，一个数组中，不会存在相同的key，
     * 且key会尽量靠前，如：
     * {[
     *  "name": "Albert",
     *  "sex": "male",
     *  "age": 21
     * ], [
     *  "name": "Shen",
     *  "sex": "female",
     * ], [
     *  "name": "Code",
     *  "sex": "male",
     * ]
     * ...
     * }
     * @return
     */
    @Override
    public String toJsonString() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < maxNumberOfValues; i++){
            JSONObject jsonObject = new JSONObject();
            for (int j = 0; j < numberOfKeys; j++){
                if (valuesArray.get(j).size() > i){
                    jsonObject.put(keys.get(j), valuesArray.get(j).get(i));
                } else {
                    jsonObject.put(keys.get(j), "");
                }
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @Override
    public String toString() {
        return this.toJsonString();
    }
}
