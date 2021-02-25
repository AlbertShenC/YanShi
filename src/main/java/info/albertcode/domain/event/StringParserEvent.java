package info.albertcode.domain.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * @Description: 字符串处理结果储存于body中
 * @Author: Albert Shen
 */

public class StringParserEvent extends Event{
    public StringParserEvent(String body){
        this.setGeneratedTime(new Date());
        this.setBody(body);
    }
}
