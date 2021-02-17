package info.albertcode.domain.task;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.procedure.Procedure;
import info.albertcode.domain.request.Request;

import java.util.List;

/**
 * @Description: 抽象任务类
 * 名称不得包含字符 . & todo:给name赋值时的合法性判断
 * @Author: Albert Shen
 */

public class Task {
    protected Integer id; // 任务id，由数据库自动生成
    protected String type; // 任务的种类
    protected String name; // 用户自定义任务的名称
    protected Request request; // 任务的请求
    protected Task preTask; // 在此任务之前的任务，其输出事件也将作为此任务的输入事件使用
    protected String inputEventProperty; // 具体获取输入事件的哪一个属性值，如Overview，Header，Body
    protected Event outputEvent; // 任务类的输出事件
    protected List<Task> nextTasks; // 下一个任务（可能包含多个）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Integer getRequestId(){
        return request.getId();
    }

    public Task getPreTask() {
        return preTask;
    }

    public void setPreTask(Task preTask) {
        this.preTask = preTask;
    }

    public Integer getPreTaskId() {
        return preTask.getId();
    }

    public String getInputEventProperty() {
        return inputEventProperty;
    }

    public void setInputEventProperty(String inputEventProperty) {
        this.inputEventProperty = inputEventProperty;
    }

    public Event getOutputEvent() {
        return outputEvent;
    }

    public void setOutputEvent(Event outputEvent) {
        this.outputEvent = outputEvent;
    }

    public Integer getOutputEventId() {
        return outputEvent.getId();
    }

    public List<Task> getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(List<Task> nextTasks) {
        this.nextTasks = nextTasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", request=" + request +
                ", preTask=" + preTask +
                ", inputEventProperty='" + inputEventProperty + '\'' +
                ", outputEvent=" + outputEvent +
                ", nextTasks=" + nextTasks +
                '}';
    }
}
