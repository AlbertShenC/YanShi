package info.albertcode.dispatch;

import info.albertcode.domain.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "yanShiCoreController")
public class YanShiCoreController {
    private YanShiCoreTimer timer;
    private YanShiCoreExecutor executor;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private WaitingQueue waitingQueue;

    private boolean isInitialized;

    @Autowired
    public YanShiCoreController(YanShiCoreTimer timer, YanShiCoreExecutor executor, ThreadPoolTaskExecutor threadPoolTaskExecutor, WaitingQueue waitingQueue) {
        this.timer = timer;
        this.executor = executor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.waitingQueue = waitingQueue;
        this.isInitialized = false;
    }

    @PostConstruct
    public void init(){
        if (this.isInitialized){
            return;
        } else {
            this.isInitialized = true;
            timer.execute();
            this.execute();
        }
    }

    private Task getNextTaskToExecute(WaitingProcedureQueue waitingProcedureQueue, WaitingTaskQueue waitingTaskQueue){
        if (waitingProcedureQueue.isEmpty()){
            if (waitingTaskQueue.isEmpty()){
                return null;
            } else {
                return waitingTaskQueue.pop();
            }
        } else {
            return waitingProcedureQueue.pop().getEntryTask();
        }
    }

    public void execute(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Task nextTaskToExecute;
                System.out.println("Controller：初始化中...");
                WaitingProcedureQueue waitingProcedureQueue = waitingQueue.getWaitingProcedureQueue();
                WaitingTaskQueue waitingTaskQueue = waitingQueue.getWaitingTaskQueue();
                System.out.println("Controller：初始化完成");
                while (true){
                    synchronized (waitingQueue){
                        try {
                            waitingQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (waitingProcedureQueue){
                            synchronized (waitingTaskQueue){
                                while ((nextTaskToExecute = getNextTaskToExecute(waitingProcedureQueue, waitingTaskQueue)) != null){
                                    executor.execute(nextTaskToExecute);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
