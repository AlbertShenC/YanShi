package info.albertcode.domain.task;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.procedure.Procedure;
import info.albertcode.domain.request.Request;

import java.util.List;

/**
 * @Description: 抽象任务类
 * @Author: Albert Shen
 */

public abstract class Task {
    private Integer id;
    protected String type; // 任务的种类
    protected String name; // 任务类的名称
    protected Request request; // 任务的请求
    protected List<Event> inputEvent; // 任务类的输入事件
    protected Event outputEvent; // 任务类的输出事件
    protected List<Task> nextTasks; // 下一个任务（可能包含多个）
    private Procedure belongedProcedure; // 所属流程

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Event> getInputEvent() {
        return inputEvent;
    }

    public void setInputEvent(List<Event> inputEvent) {
        this.inputEvent = inputEvent;
    }

    public Event getOutputEvent() {
        return outputEvent;
    }

    public void setOutputEvent(Event outputEvent) {
        this.outputEvent = outputEvent;
    }

    public List<Task> getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(List<Task> nextTasks) {
        this.nextTasks = nextTasks;
    }

    public Procedure getBelongedProcedure() {
        return belongedProcedure;
    }

    public void setBelongedProcedure(Procedure belongedProcedure) {
        this.belongedProcedure = belongedProcedure;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", request=" + request +
                ", inputEvent=" + inputEvent +
                ", outputEvent=" + outputEvent +
                ", nextTasks=" + nextTasks +
                ", belongedProcedure=" + belongedProcedure +
                '}';
    }
}
