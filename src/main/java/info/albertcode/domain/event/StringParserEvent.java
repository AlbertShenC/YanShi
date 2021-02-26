package info.albertcode.domain.event;

import info.albertcode.utils.enums.ETaskType;

import java.util.Date;

/**
 * @Description: 字符串处理结果储存于body中
 * @Author: Albert Shen
 */

public class StringParserEvent extends Event{
    public StringParserEvent(String body){
        this.setTypeEnum(ETaskType.StringParser);
        this.setGeneratedTime(new Date());
        this.setBody(body);
    }
}
