package info.albertcode.dispatch;

import info.albertcode.domain.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "controller")
public class ProcedureController {
    private ProcedureQueue procedureQueue;
    private TaskQueue taskQueue;
    private ProcedureTimer timer;
    private TaskExecutor executor;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private MutexLock mutexLock; // 两个消息队列的同步锁
    private ConsumerLock consumerLock; // 通知Controller处理队列的信号量

    @Autowired
    public ProcedureController(ProcedureQueue procedureQueue, TaskQueue taskQueue, ProcedureTimer timer, TaskExecutor executor, ThreadPoolTaskExecutor threadPoolTaskExecutor, MutexLock mutexLock, ConsumerLock consumerLock) {
        this.procedureQueue = procedureQueue;
        this.taskQueue = taskQueue;
        this.timer = timer;
        this.executor = executor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.mutexLock = mutexLock;
        this.consumerLock = consumerLock;
    }

    public void init(){
        timer.execute();
        this.execute();
    }

    private Task getNextTaskToExecute(){
        if (procedureQueue.isEmpty()){
            if (taskQueue.isEmpty()){
                return null;
            } else {
                return taskQueue.pop();
            }
        } else {
            return procedureQueue.pop().getEntryTask();
        }
    }

    //todo:可能存在bug，几个线程都在notify Controller的时候，会不会因为Controller执行时间过长，漏掉了一些notify，可能要将if改成while
    public void execute(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Task nextTaskToExecute;
                System.out.println("Controller：初始化中...");
                while (true){
                    synchronized (consumerLock){
                        try {
                            consumerLock.wait();
                            System.out.println("Controller：是谁召唤了我？");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (mutexLock){
                            while ((nextTaskToExecute = getNextTaskToExecute()) != null){
                                System.out.println("Controller：获取到任务，TaskName = " + nextTaskToExecute.getName());
                                executor.execute(nextTaskToExecute);
                                System.out.println("Controller：Executor，我的仆人，执行它...");
                            }
                        }
                    }
                }
            }
        });
    }
}
