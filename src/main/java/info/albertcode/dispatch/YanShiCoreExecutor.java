package info.albertcode.dispatch;

import info.albertcode.domain.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "yanShiCoreExecutor")
public class YanShiCoreExecutor {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private TaskQueue taskQueue;
    private MutexLock mutexLock;
    private ConsumerLock consumerLock;

    @Autowired
    public YanShiCoreExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor, TaskQueue taskQueue, MutexLock mutexLock, ConsumerLock consumerLock) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.taskQueue = taskQueue;
        this.mutexLock = mutexLock;
        this.consumerLock = consumerLock;
    }

    private Integer i = 0;

    public void execute(Task taskToExecute){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("    Executor" + Thread.currentThread() + "：我被创建了");
                try {
                    System.out.println("    Executor" + Thread.currentThread() + "：正在执行任务，taskName = " + taskToExecute.getName());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Task taskGenerated = new Task();
                synchronized (consumerLock){
                    taskGenerated.setName("这是Executor产生的任务" + i);
                    taskGenerated.setId(i);
                    i++;
                }
                System.out.println("    Executor" + Thread.currentThread() + "：创建完成第" + taskGenerated.getId() + "个任务");

                synchronized (consumerLock){
                    synchronized (mutexLock){
                        taskQueue.push(taskGenerated);
                        System.out.println("    Executor" + Thread.currentThread() + "：将第" + taskGenerated.getId() + "个任务加入Queue");
                    }
                    consumerLock.notify();
                    System.out.println("    Executor" + Thread.currentThread() + "：唤醒Controller...");
                }
            }
        });
    }
}
