package info.albertcode.domain.event;

import java.util.Date;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class RssGenerateEvent extends Event{
    public RssGenerateEvent(String body) {
        this.type = "RssGenerate";
        this.generatedTime = new Date();
        this.body = body;
    }
}
