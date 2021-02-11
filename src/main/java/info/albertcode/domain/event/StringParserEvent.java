package info.albertcode.domain.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class StringParserEvent extends Event{
    public StringParserEvent(List<String> keys, List<List<String>> values){
        this.type = "StringParser";
        this.generatedTime = new Date();
        JSONArray array = new JSONArray();
        //todo:存在bug，待修复：如果一个key同时匹配到多个字符串，他们会组成一个json数组，
        // 而实际上应该是不同的key组成一个数组，而相同的key应该分别位于不同数组中
        for (int i = 0; i < keys.size(); i++){
            JSONObject object = new JSONObject();
            for (int j = 0; j < values.get(i).size(); j++){
                object.put(keys.get(i), values.get(i).get(j));
            }
            array.add(object);
        }
        this.body = array.toJSONString();
    }
}
