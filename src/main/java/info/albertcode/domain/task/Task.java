package info.albertcode.domain.task;

import info.albertcode.domain.event.Event;

import java.util.List;

/**
 * @Description: 抽象任务类
 * @Author: Albert Shen
 */

public abstract class Task {
    private Integer id;
    protected String name; // 任务类的名称
    protected Event inputEvent; // 任务类的输入事件
    protected Event outputEvent; // 任务类的输出事件
    protected List<Event> nextEvents; // 下一个任务（可能包含多个）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event getInputEvent() {
        return inputEvent;
    }

    public Event getOutputEvent() {
        return outputEvent;
    }

    public List<Event> getNextEvents() {
        return nextEvents;
    }
}
