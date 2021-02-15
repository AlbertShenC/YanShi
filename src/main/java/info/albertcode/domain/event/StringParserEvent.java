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
    public StringParserEvent(String body){
        this.type = "StringParser";
        this.generatedTime = new Date();
        this.body = body;
    }

}
