package info.albertcode.domain.task;

/**
 * @Description: 解析指定事件
 * Request 指定解析的基本信息以及解析哪一个事件
 * InputEvent 有且只有一个，需要被解析的事件，采用 Task名称.事件属性 来指定
 *            如 百度搜索Albert.body，可指定的属性包括 overview，header，body
 *            多个事件使用 & 连接
 * @Author: Albert Shen
 */

public class StringParserTask extends Task{
    public StringParserTask() {
        this.type = "StringParser";
    }
}
