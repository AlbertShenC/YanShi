package info.albertcode.domain.task;

import info.albertcode.domain.event.Event;

import java.util.List;

/**
 * @Description: 发起Http请求的任务类
 * @Author: Albert Shen
 */

public class HttpRequestTask extends Task{
    public void setName(String name) {
        this.name = name;
    }

    public void setInputEvent(Event inputEvent) {
        this.inputEvent = inputEvent;
    }

    public void setOutputEvent(Event outputEvent) {
        this.outputEvent = outputEvent;
    }

    public void setNextEvents(List<Event> nextEvents) {
        this.nextEvents = nextEvents;
    }
}
