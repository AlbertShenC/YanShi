package info.albertcode.domain.procedure;

import info.albertcode.domain.task.Task;

import java.time.LocalDateTime;

/**
 * @Description: 流程，多个任务一起组成流程，一整个统一的逻辑块
 * @Author: Albert Shen
 */

public class Procedure {
    private Integer id;
    private String name;
    private Task entryTask; // 流程的入口任务，流程只记录自己的开始任务是什么
                            // 通过任务中的下一个任务列表来获取全部任务
    private InitTime initTime; // 定时启动时间/启动间隔
    private LocalDateTime lastExecuteDateTime; // 上一次申请开始执行的时间
    private LocalDateTime expectExecuteDateTime; // 预计下一次执行时间


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

    public Task getEntryTask() {
        return entryTask;
    }

    public void setEntryTask(Task entryTask) {
        this.entryTask = entryTask;
    }

    public Integer getEntryTaskId() {
        return this.getEntryTask().getId();
    }

    public InitTime getInitTime() {
        return initTime;
    }

    public void setInitTime(InitTime initTime) {
        this.initTime = initTime;
    }

    public LocalDateTime getLastExecuteDateTime() {
        return lastExecuteDateTime;
    }

    public void setLastExecuteDateTime(LocalDateTime lastExecuteDateTime) {
        this.lastExecuteDateTime = lastExecuteDateTime;
    }

    public LocalDateTime getExpectExecuteDateTime() {
        return expectExecuteDateTime;
    }

    public void calculateExpectExecuteDateTime() {
        this.expectExecuteDateTime = this.getInitTime().getNextExecuteDate(this.getLastExecuteDateTime());
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", entryTask=" + entryTask +
                ", initTime=" + initTime +
                ", lastExecuteDateTime=" + lastExecuteDateTime +
                ", expectExecuteDateTime=" + expectExecuteDateTime +
                '}';
    }
}
