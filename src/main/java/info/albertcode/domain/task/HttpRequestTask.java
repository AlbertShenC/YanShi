package info.albertcode.domain.task;

import info.albertcode.utils.enums.ETaskType;

/**
 * @Description: 发起Http请求的任务类
 * @Author: Albert Shen
 */

public class HttpRequestTask extends Task{
    public HttpRequestTask() {
        this.typeEnum = ETaskType.HttpRequest;
    }
}
