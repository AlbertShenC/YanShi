package info.albertcode.utils.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class JSONConverter {

    public static JSONArray KeyValuesToJsonArray(KeyValues keyValues){
        List<String> keys = keyValues.getKeys();
        List<List<Object>> valuesArray = keyValues.getValuesArray();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < keyValues.getNumberOfKeys(); i++){
            JSONObject jsonObject = new JSONObject();
            for (int j = 0; j < valuesArray.get(i).size(); j++){
                jsonObject.put(keys.get(i), valuesArray.get(i).get(j));
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
