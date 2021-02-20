package info.albertcode.dispatch;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import info.albertcode.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "yanShiCoreExecutor")
public class YanShiCoreExecutor {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private WaitingQueue waitingQueue;
    private ITaskService taskService;

    @Autowired
    public YanShiCoreExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor, WaitingQueue waitingQueue, ITaskService taskService) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.waitingQueue = waitingQueue;
        this.taskService = taskService;
    }

    public void execute(Task taskToExecute){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                WaitingTaskQueue waitingTaskQueue = waitingQueue.getWaitingTaskQueue();
                try {
                    System.out.println("Executor " + Thread.currentThread() + "：正在执行任务，taskName = " + taskToExecute.getName());
                    taskService.executeTask(taskToExecute.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                List<Task> taskGenerated = taskToExecute.getNextTasks();
                synchronized (waitingTaskQueue){
                    for (Task task : taskGenerated){
                        waitingTaskQueue.push(task);
                    }
                }

                synchronized (waitingQueue){
                    waitingQueue.notify();
                }
            }
        });
    }
}
