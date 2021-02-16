package info.albertcode.utils.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class KeyValues {
    private List<String> keys;
    private List<List<Object>> valuesArray;
    private Integer numberOfKeys;
    private Integer maxNumberOfValues;

    public KeyValues() {
        keys = new ArrayList<String>();
        valuesArray = new ArrayList<>();
        numberOfKeys = 0;
        maxNumberOfValues = 0;
    }

    private Integer findKeySubscript(String key){
        for (Integer i = 0; i < keys.size(); i++){
            if (key.equals(keys.get(i))){
                return i;
            }
        }
        return -1;
    }

    public Integer addKey(String key){
        if (findKeySubscript(key) == -1){
            this.keys.add(key);
            List<Object> values = new ArrayList<Object>();
            valuesArray.add(values);
            numberOfKeys += 1;
        }
        return numberOfKeys - 1;
    }

    public void addValue(int subscript, Object value){
        valuesArray.get(subscript).add(value);
        Integer numberOfValues = valuesArray.get(subscript).size();
        if (numberOfValues > maxNumberOfValues){
            maxNumberOfValues = numberOfValues;
        }
    }

    public void addValue(Integer subscript, Object value){
        addValue(subscript.intValue(), value);
    }

    public void addValue(String key, Object value){
        Integer subscript = findKeySubscript(key);
        if (subscript == -1){
            subscript = addKey(key);
        }
        addValue(subscript, value);
    }

    public void deleteKey(int subscript){
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

    public void deleteKey(Integer subscript){
        deleteKey(subscript.intValue());
    }

    public void deleteKey(String key){
        Integer subscript = findKeySubscript(key);
        if (subscript != -1){
            deleteKey(subscript);
        }
    }

    public Integer getNumberOfKeys() {
        return numberOfKeys;
    }

    public Integer getMaxNumberOfValues() {
        return maxNumberOfValues;
    }

    public List<String> getKeys() {
        return keys;
    }

    public List<List<Object>> getValuesArray() {
        return valuesArray;
    }

    public JSONArray toJSONArray(){
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
        return jsonArray;
    }

    @Override
    public String toString() {
        return "KeyValues{" +
                "keys=" + keys +
                ", valuesArray=" + valuesArray +
                ", numberOfKeys=" + numberOfKeys +
                ", maxNumberOfValues=" + maxNumberOfValues +
                '}';
    }
}
