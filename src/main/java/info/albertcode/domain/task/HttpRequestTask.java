package info.albertcode.domain.task;

import info.albertcode.domain.event.Event;

import java.util.List;

/**
 * @Description: 发起Http请求的任务类
 * @Author: Albert Shen
 */

public class HttpRequestTask extends Task{
    public HttpRequestTask() {
        this.type = "HttpRequest";
    }
}
