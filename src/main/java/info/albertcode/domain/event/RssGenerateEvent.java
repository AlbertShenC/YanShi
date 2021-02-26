package info.albertcode.domain.event;

import info.albertcode.utils.enums.ETaskType;

import java.util.Date;

/**
 * @Description: 生成的rss的xml字符串储存于body中
 * @Author: Albert Shen
 */

public class RssGenerateEvent extends Event{
    public RssGenerateEvent(String body) {
        this.setTypeEnum(ETaskType.RssGenerate);
        this.setGeneratedTime(new Date());
        this.setBody(body);
    }
}
