package info.albertcode.domain.procedure;

import info.albertcode.domain.task.Task;

import java.time.LocalDateTime;

/**
 * @Description: 流程，多个任务一起组成流程，一整个统一的逻辑块
 * @Author: Albert Shen
 */

public class Procedure {
    private Integer id;
    private Task entryTask; // 流程的入口任务，流程只记录自己的开始任务是什么
                            // 通过任务中的下一个任务列表来获取全部任务
    private InitTime initTime; // 定时启动时间/启动间隔
    private LocalDateTime lastExecuteDateTime; // 上一次申请开始执行的时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Task getEntryTask() {
        return entryTask;
    }

    public void setEntryTask(Task entryTask) {
        this.entryTask = entryTask;
    }

    public Integer getEntryTaskId() {
        return entryTask.getId();
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
}
