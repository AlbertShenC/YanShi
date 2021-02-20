package info.albertcode.domain.task;

import info.albertcode.utils.enums.ETaskType;

/**
 * @Description: 解析指定事件
 * @Author: Albert Shen
 */

public class StringParserTask extends Task{
    public StringParserTask() {
        this.typeEnum = ETaskType.StringParser;
    }
}
