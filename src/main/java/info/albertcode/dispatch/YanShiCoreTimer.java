package info.albertcode.dispatch;

import info.albertcode.dao.IProcedureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "yanShiCoreTimer")
public class YanShiCoreTimer {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private WaitingQueue waitingQueue;
    private RegisterProcedureQueue registerProcedureQueue;
    private IProcedureDao procedureDao;

    @Autowired
    public YanShiCoreTimer(ThreadPoolTaskExecutor threadPoolTaskExecutor, WaitingQueue waitingQueue, RegisterProcedureQueue registerProcedureQueue, IProcedureDao procedureDao) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.waitingQueue = waitingQueue;
        this.registerProcedureQueue = registerProcedureQueue;
        this.procedureDao = procedureDao;
    }

    //todo:执行前需要计算一下应该什么时刻执行指定任务
    public void registerProcedure(Integer procedureId){
        synchronized (registerProcedureQueue){
            registerProcedureQueue.push(procedureId);
            registerProcedureQueue.notify();
        }
    }

    public void execute(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Timer：初始化中...");
                WaitingProcedureQueue waitingProcedureQueue = waitingQueue.getWaitingProcedureQueue();
                System.out.println("Timer：初始化完成");
                while (true){
                    synchronized (registerProcedureQueue){
                        try {
                            registerProcedureQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (waitingQueue){
                            while (!registerProcedureQueue.isEmpty()){
                                waitingProcedureQueue.push(
                                        procedureDao.findProcedureById(registerProcedureQueue.pop())
                                );
                            }
                            waitingQueue.notify();
                        }
                    }
                }
            }
        });
    }
}
