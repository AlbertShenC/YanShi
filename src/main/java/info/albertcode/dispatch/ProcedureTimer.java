package info.albertcode.dispatch;

import info.albertcode.domain.procedure.Procedure;
import info.albertcode.domain.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "timer")
public class ProcedureTimer {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private ProcedureQueue procedureQueue;
    private MutexLock mutexLock;
    private ConsumerLock consumerLock;
    private TimerLock timerLock;

    @Autowired
    public ProcedureTimer(ThreadPoolTaskExecutor threadPoolTaskExecutor, ProcedureQueue procedureQueue, MutexLock mutexLock, ConsumerLock consumerLock, TimerLock timerLock) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.procedureQueue = procedureQueue;
        this.mutexLock = mutexLock;
        this.consumerLock = consumerLock;
        this.timerLock = timerLock;
    }

    public void registerProcedure(Procedure procedure){
        synchronized (timerLock){

        }
    }

    public void execute(){
        threadPoolTaskExecutor.execute(new Runnable() {
            Integer i = 0;

            @Override
            public void run() {
                System.out.println("  Timer：初始化中...");
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Procedure procedure = new Procedure();
                    Task task = new Task();
                    task.setName("这是Timer创建的流程" + i + "的首个任务");
                    procedure.setEntryTask(task);
                    System.out.println("  Timer：创建完成第" + i + "个流程");


                    synchronized (consumerLock){
                        synchronized (mutexLock){
                            procedureQueue.push(procedure);
                            System.out.println("  Timer：将第" + i + "个流程加入ProcedureQueue");
                        }
                        consumerLock.notify();
                        System.out.println("  Timer：唤醒Controller中...");
                    }

                    i++;
                }
            }
        });
    }
}
