package info.albertcode.domain.task;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.request.Request;
import info.albertcode.utils.enums.ETaskType;

import java.util.List;

/**
 * @Description: 抽象任务类
 * 名称不得包含字符 . & % todo:给name赋值时的合法性判断
 * @Author: Albert Shen
 */

public class Task {
    private Integer id; // 任务id，由数据库自动生成
    private ETaskType typeEnum; // 任务的种类
    private String name; // 用户自定义任务的名称
    private Request request; // 任务的请求
    private Task inputTask; // 在此任务之前的任务，其输出事件也将作为此任务的输入事件使用
    private String inputEventProperty; // 具体获取输入事件的哪一个属性值，如Overview，Header，Body
    private Event outputEvent; // 任务类的输出事件
    private List<Task> nextTasks; // 下一个任务（可能包含多个）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ETaskType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(ETaskType typeEnum) {
        this.typeEnum = typeEnum;
    }

    public Integer getType() {
        return this.getTypeEnum().getValue();
    }

    public void setType(Integer typeValue) {
        this.typeEnum = ETaskType.valueOf(typeValue);
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
        return this.getRequest().getId();
    }

    public Task getInputTask() {
        return inputTask;
    }

    public void setInputTask(Task inputTask) {
        this.inputTask = inputTask;
    }

    public Integer getInputTaskId() {
        return this.getInputTask().getId();
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
        return this.getOutputEvent().getId();
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
                ", typeEnum=" + typeEnum +
                ", name='" + name + '\'' +
                ", request=" + request +
                ", inputTask=" + inputTask +
                ", inputEventProperty='" + inputEventProperty + '\'' +
                ", outputEvent=" + outputEvent +
                '}';
    }
}
